package com.reflectionsoftware.service.converter;

import com.reflectionsoftware.service.file.FileService;
import com.reflectionsoftware.util.validator.JsonValidator;

import java.io.File;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class JsonConverter {

    // Método principal para converter JSON para Java
    public static void convertJsonToJava(File jsonFile, File javaDirectory) {
        Map<String, Object> jsonRootMap = JsonValidator.convertJsonToMap(jsonFile);
        FileService.resetDirectory(javaDirectory);

        List<Map<String, Object>> steps = getSteps(jsonRootMap);
        if (steps != null) {
            for (Map<String, Object> step : steps) {
                File stepDirectory = createStepDirectory(javaDirectory, (String) step.get("passo"));
                processStepClasses(step, stepDirectory, jsonRootMap);
            }
        }
    }

    // Processa as classes de um passo
    private static void processStepClasses(Map<String, Object> step, File stepDirectory, Map<String, Object> jsonRootMap) {
        List<Map<String, Object>> classes = getClassList(step);
        if (classes != null) {
            for (Map<String, Object> clazz : classes) {
                String classContent = generateClassContent(clazz, (Map<String, Object>) jsonRootMap.get("criterios_gerais"));
                writeClassToFile(stepDirectory, clazz, classContent);
            }
        }
    }

    // Obtém a lista de passos do JSON
    private static List<Map<String, Object>> getSteps(Map<String, Object> jsonRootMap) {
        return jsonRootMap.get("passos") instanceof List ? (List<Map<String, Object>>) jsonRootMap.get("passos") : null;
    }

    // Cria o diretório do passo
    private static File createStepDirectory(File javaDirectory, String stepName) {
        File stepDirectory = new File(javaDirectory, stepName);
        FileService.createDirectory(stepDirectory);
        return stepDirectory;
    }

    // Obtém a lista de classes de um passo
    private static List<Map<String, Object>> getClassList(Map<String, Object> step) {
        return step.get("classes") instanceof List ? (List<Map<String, Object>>) step.get("classes") : null;
    }

    // Gera o conteúdo da classe Java
    private static String generateClassContent(Map<String, Object> classObject, Map<String, Object> generalCriteria) {
        String className = (String) classObject.get("nome_da_classe");

        boolean allAttributesPrivate = getBooleanFromMap(generalCriteria, "atributos_todos_privados", false);
        boolean requiredConstructor = getBooleanFromMap(generalCriteria, "construtores_obrigatorios", false);
        double nameSimilarityThreshold = getDoubleFromMap(generalCriteria, "similaridade_nomes", 1);

        StringBuilder classContent = new StringBuilder();
        appendClassComments(classContent, allAttributesPrivate, requiredConstructor, nameSimilarityThreshold, classObject);

        classContent.append("public class ").append(className).append(" {\n\n");

        generateAttributes(classContent, classObject, allAttributesPrivate);
        generateConstructors(classContent, classObject, requiredConstructor);
        generateMethods(classContent, classObject);

        classContent.append("}\n");
        return classContent.toString();
    }

    // Adiciona comentários de configuração no cabeçalho da classe
    private static void appendClassComments(StringBuilder classContent, boolean allAttributesPrivate,
                                            boolean requiredConstructor, double nameSimilarityThreshold,
                                            Map<String, Object> classObject) {
        classContent.append("// Atributos todos privados: ").append(allAttributesPrivate).append("\n")
                .append("// Construtores obrigatórios: ").append(requiredConstructor).append("\n")
                .append("// Similaridade dos nomes: ").append(nameSimilarityThreshold).append("\n\n")
                .append("// Especificação dos atributos: ").append(classObject.get("especificacao_atributos")).append("\n")
                .append("// Especificação dos construtores: ").append(classObject.get("especificacao_construtores")).append("\n")
                .append("// Especificação dos métodos: ").append(classObject.get("especificacao_metodos")).append("\n\n");
    }

    // Gera os atributos da classe
    private static void generateAttributes(StringBuilder classContent, Map<String, Object> classObject, boolean allAttributesPrivate) {
        List<Map<String, Object>> attributes = (List<Map<String, Object>>) classObject.get("atributos");
        if (attributes != null) {
            for (Map<String, Object> attribute : attributes) {
                classContent.append("\n    @SuppressWarnings(\"PONTOS:").append(getDoubleFromMap(attribute, "nota", 0)).append("\")\n");
                String visibility = allAttributesPrivate ? "private" : "";
                if (getBooleanFromMap(attribute, "static", false)) visibility += " static";
                if (getBooleanFromMap(attribute, "final", false)) visibility += " final";

                classContent.append("    ").append(visibility).append(" ")
                        .append(attribute.get("tipo")).append(" ")
                        .append(attribute.get("nome")).append(";")
                        .append(" // Nota: ").append(getDoubleFromMap(attribute, "nota", 0)).append("\n");
            }
            classContent.append("\n");
        }
    }

    // Gera os construtores da classe
    private static void generateConstructors(StringBuilder classContent, Map<String, Object> classObject, boolean requiredConstructor) {
        List<Map<String, Object>> constructors = (List<Map<String, Object>>) classObject.get("construtores");
        if (constructors != null) {
            for (Map<String, Object> constructor : constructors) {
                appendConstructor(classContent, classObject, constructor);
            }
        } else if (requiredConstructor) {
            appendDefaultConstructor(classContent, (String) classObject.get("nome_da_classe"));
        }
    }

    // Adiciona um construtor ao conteúdo
    private static void appendConstructor(StringBuilder classContent, Map<String, Object> classObject, Map<String, Object> constructor) {
        classContent.append("    // Nota: ").append(getDoubleFromMap(constructor, "nota", 0)).append("\n");
        classContent.append("    public ").append(classObject.get("nome_da_classe")).append("(");
        generateParameters(classContent, constructor);
        classContent.append(") {\n");
        setConstructorParameters(classContent, constructor, (List<Map<String, Object>>) classObject.get("atributos"));
        classContent.append("    }\n\n");
    }

    // Adiciona um construtor padrão vazio
    private static void appendDefaultConstructor(StringBuilder classContent, String className) {
        classContent.append("    public ").append(className).append("() {\n        // Construtor vazio\n    }\n\n");
    }

    // Gera os métodos da classe
    private static void generateMethods(StringBuilder classContent, Map<String, Object> classObject) {
        List<Map<String, Object>> methods = (List<Map<String, Object>>) classObject.get("metodos");
        if (methods != null) {
            for (Map<String, Object> method : methods) {
                appendMethod(classContent, method);
            }
        }
    }

    // Adiciona um método ao conteúdo
    private static void appendMethod(StringBuilder classContent, Map<String, Object> method) {
        classContent.append("    // Nota: ").append(getDoubleFromMap(method, "nota", 0)).append("\n");
        classContent.append("    ").append(method.get("visibilidade")).append(" ")
                .append(method.get("retorno")).append(" ")
                .append(method.get("nome")).append("(");
        generateParameters(classContent, method);
        classContent.append(") {\n        // Implementação\n");
        if (!"void".equals(method.get("retorno"))) {
            classContent.append("        return null;\n");
        }
        classContent.append("    }\n\n");
    }

    // Gera parâmetros para métodos ou construtores
    private static void generateParameters(StringBuilder classContent, Map<String, Object> element) {
        Map<String, String> parameters = (Map<String, String>) element.get("parametros");
        if ((parameters != null) && (!parameters.isEmpty())) {
            for (Map.Entry<String, String> param : parameters.entrySet()) {
                classContent.append(param.getValue()).append(" ").append(param.getKey()).append(", ");
            }
            classContent.setLength(classContent.length() - 2); // Remove vírgula extra
        }
    }

    private static void setConstructorParameters(StringBuilder classContent, Map<String, Object> constructor, List<Map<String, Object>> attributes) {
        Map<String, String> parameters = (Map<String, String>) constructor.get("parametros");
        if (parameters != null) {
            for (String paramName : parameters.keySet()) {
                boolean hasAttribute = false;
                // Verifica se o parâmetro existe nos atributos
                for (Map<String, Object> attribute : attributes) {
                    if (attribute.get("nome").equals(paramName)) {
                        hasAttribute = true;
                        break;
                    }
                }
    
                // Se o atributo existe, faz a atribuição. Caso contrário, coloca um comentário
                if (hasAttribute) {
                    classContent.append("        this.").append(paramName).append(" = ").append(paramName).append(";\n");
                } else {
                    classContent.append("        // this.").append(paramName).append(" = ").append(paramName).append(";\n");
                }
            }
        }
    }
    

    private static void writeClassToFile(File stepDirectory, Map<String, Object> classObject, String classContent) {
        FileService.writeFile(new File(stepDirectory, classObject.get("nome_da_classe") + ".java"), classContent);
    }

    // Obtém valores booleanos do mapa com valor padrão
    private static boolean getBooleanFromMap(Map<String, Object> map, String key, boolean defaultValue) {
        return map.containsKey(key) ? (boolean) map.get(key) : defaultValue;
    }

    // Obtém valores double do mapa com valor padrão
    private static double getDoubleFromMap(Map<String, Object> map, String key, double defaultValue) {
        return map.containsKey(key) ? ((Number) map.get(key)).doubleValue() : defaultValue;
    }
}
