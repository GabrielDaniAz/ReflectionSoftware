package com.reflectionsoftware.model.result.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import com.reflectionsoftware.model.criteria.Attribute;
import com.reflectionsoftware.model.criteria.CorrectionCriteria;
import com.reflectionsoftware.model.criteria.CriteriaByClass;
import com.reflectionsoftware.model.criteria.GeneralCriteria;
import com.reflectionsoftware.service.CorrectionCriteriaManager;

/**
 * Classe que analisa uma classe Java usando reflexão.
 * Fornece métodos para carregar informações sobre a classe,
 * verificar critérios de correção e gerar resultados de correção.
 */
public class ReflectionClassAnalyses {

    private Class<?> clazz; // Classe a ser analisada.
    private Method[] methods; // Métodos da classe.
    private Field[] fields; // Atributos da classe.
    private Constructor<?>[] constructors; // Construtores da classe.
    private Class<?> superClass; // Superclasse da classe.
    private Class<?>[] interfaces; // Interfaces implementadas pela classe.

    /**
     * Construtor que inicializa a análise da classe.
     *
     * @param clazz Classe a ser analisada.
     */
    public ReflectionClassAnalyses(Class<?> clazz) {
        this.clazz = clazz;
        loadClassInformation(); // Carrega as informações da classe ao inicializar
    }

    /**
     * Carrega informações detalhadas sobre a classe,
     * incluindo superclasse, interfaces, campos e métodos.
     */
    private void loadClassInformation() {
        superClass = clazz.getSuperclass();
        interfaces = clazz.getInterfaces();
        constructors = clazz.getDeclaredConstructors();
        fields = clazz.getDeclaredFields();
        methods = clazz.getDeclaredMethods();
    }

    /**
     * Retorna o nome da classe sendo analisada.
     *
     * @return O nome da classe.
     */
    private String getClazzName() {
        return clazz.getSimpleName();
    }

    /**
     * Verifica se todos os atributos da classe são privados.
     *
     * @return true se todos os atributos são privados, caso contrário false.
     */
    private boolean isAllAttributesPrivate() {
        return Arrays.stream(fields)
            .allMatch(field -> Modifier.isPrivate(field.getModifiers()));
    }

    /**
     * Verifica se a classe possui um número específico de atributos.
     *
     * @param expectedNumber Número esperado de atributos.
     * @return true se o número de atributos corresponde ao esperado, caso contrário false.
     */
    private boolean hasNumberOfAttributes(int expectedNumber) {
        return fields.length == expectedNumber;
    }

    /**
     * Verifica se a classe possui construtores.
     *
     * @return true se a classe possui construtores, caso contrário false.
     */
    private boolean hasConstructors() {
        return constructors.length > 0;
    }

    /**
     * Verifica se um atributo específico existe e se é do tipo correto.
     *
     * @param attributeName Nome do atributo.
     * @param typeName Nome do tipo esperado.
     * @return true se o atributo existe e é do tipo correto, caso contrário false.
     */
    private boolean hasAttributeOfType(String attributeName, String typeName) {
        try {
            Field field = clazz.getDeclaredField(attributeName);
            return field.getType().getSimpleName().equals(typeName);
        } catch (NoSuchFieldException e) {
            return false; // Atributo não encontrado
        }
    }

    /**
     * Retorna informações detalhadas sobre a classe,
     * incluindo nome, superclasse, interfaces, campos e métodos.
     *
     * @return Uma string com as informações detalhadas da classe.
     */
    public String getDetailedInformation() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome da Classe: ").append(getClazzName()).append("\n");
        sb.append("Super Classe: ").append(superClass != null ? superClass.getSimpleName() : "None").append("\n");

        sb.append("Interfaces: ");
        if (interfaces.length > 0) {
            for (Class<?> iface : interfaces) {
                sb.append(iface.getSimpleName()).append(" ");
            }
        } else {
            sb.append("None");
        }
        sb.append("\n");

        sb.append("Atributos: \n");
        if (fields.length > 0) {
            for (Field field : fields) {
                sb.append("- ").append(field.getName()).append(" : ").append(field.getType().getSimpleName());
                sb.append(" (").append(Modifier.toString(field.getModifiers())).append(")\n");
            }
        } else {
            sb.append("Nenhum atributo nesta classe (").append(getClazzName()).append(").\n");
        }

        sb.append("Construtores: \n");
        if (constructors.length > 0) {
            for (Constructor<?> constructor : constructors) {
                sb.append("- ").append(constructor.getName()).append(" (");
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    sb.append(parameterTypes[i].getSimpleName());
                    if (i < parameterTypes.length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(")\n");
            }
        } else {
            sb.append("A classe não possui nenhum construtor.\n");
        }

        sb.append("Métodos: \n");
        if (methods.length > 0) {
            for (Method method : methods) {
                sb.append("- ").append(method.getName()).append(" : ")
                  .append(method.getReturnType().getSimpleName());
                sb.append(" (").append(Modifier.toString(method.getModifiers())).append(")\n");
            }
        } else {
            sb.append("A classe não possui métodos.\n");
        }

        return sb.toString();
    }

    /**
     * Executa a correção da classe de acordo com os critérios de correção definidos.
     *
     * @return Uma string com os resultados da correção.
     */
    public String correction() {
        CorrectionCriteria correctionCriteria = CorrectionCriteriaManager.getCorrectionCriteria();
        List<GeneralCriteria> generalCriterias = correctionCriteria.getGeneralCriterias();
        List<CriteriaByClass> criteriaByClasses = correctionCriteria.getCriteriaByClasses();

        StringBuilder sb = new StringBuilder();

        boolean hasClass = false;

        for (CriteriaByClass criteriaByClass : criteriaByClasses) {
            if (criteriaByClass.getClassName().equals(getClazzName())) {
                hasClass = true;
                sb.append("Classe ").append(getClazzName().toUpperCase()).append(" encontrada.\n");

                if (generalCriterias.contains(GeneralCriteria.ATRIBUTOS_TODOS_PRIVADOS)) {
                    if (isAllAttributesPrivate()) 
                        sb.append("- Todos os atributos são privados.\n");
                    else 
                        sb.append("- Nem todos os atributos são privados.\n");
                }
                if (generalCriterias.contains(GeneralCriteria.CONSTRUTORES_OBRIGATORIOS)) {
                    if (hasConstructors()) 
                        sb.append("- Possui construtores.\n");
                    else 
                        sb.append("- Não possui construtores.\n");
                }

                int expectedNumberOfAttributes = criteriaByClass.getNumberOfAttributes();

                if (hasNumberOfAttributes(expectedNumberOfAttributes)) 
                    sb.append("- Possui a quantidade de atributos esperado: ").append(expectedNumberOfAttributes).append("\n");
                else 
                    sb.append("- Não possui a quantidade de atributos esperado: ").append(expectedNumberOfAttributes).append("\n");

                for (Attribute attribute : criteriaByClass.getAttributes()) {
                    if (hasAttributeOfType(attribute.getName(), attribute.getType())) 
                        sb.append("- O atributo ").append(attribute.getName())
                          .append(" é do tipo ").append(attribute.getType()).append(".\n");
                    else 
                        sb.append("- Não há o atributo ").append(attribute.getName())
                          .append(" ou não é do tipo ").append(attribute.getType()).append(".\n");
                }
                break;
            }
        }

        if (!hasClass) 
            sb.append("A classe ").append(getClazzName()).append(" não foi encontrada nos critérios de correção.\n");

        return sb.toString();
    }

    /**
     * Executa a correção da classe de acordo com os critérios de correção definidos,
     * retornando um resultado estruturado.
     *
     * @return Um objeto CorrectionResult com os resultados da correção.
     */
    public CorrectionResult performCorrection() {
        CorrectionCriteria correctionCriteria = CorrectionCriteriaManager.getCorrectionCriteria();
        List<GeneralCriteria> generalCriterias = correctionCriteria.getGeneralCriterias();
        List<CriteriaByClass> criteriaByClasses = correctionCriteria.getCriteriaByClasses();

        CorrectionResult result = new CorrectionResult(getClazzName());

        boolean hasClass = false;

        for (CriteriaByClass criteriaByClass : criteriaByClasses) {
            if (criteriaByClass.getClassName().equals(getClazzName())) {
                hasClass = true;
                result.addMessage("Classe " + getClazzName().toUpperCase() + " encontrada. ✅");

                if (generalCriterias.contains(GeneralCriteria.ATRIBUTOS_TODOS_PRIVADOS)) {
                    if (isAllAttributesPrivate()) 
                        result.addMessage("Todos os atributos são privados. ✅");
                    else 
                        result.addMessage("Nem todos os atributos são privados. ❌");
                }
                if (generalCriterias.contains(GeneralCriteria.CONSTRUTORES_OBRIGATORIOS)) {
                    if (hasConstructors()) 
                        result.addMessage("Possui construtores. ✅");
                    else 
                        result.addMessage("Não possui construtores. ❌");
                }

                int expectedNumberOfAttributes = criteriaByClass.getNumberOfAttributes();

                if (hasNumberOfAttributes(expectedNumberOfAttributes)) 
                    result.addMessage("Possui a quantidade de atributos esperada: " + expectedNumberOfAttributes + " ✅");
                else 
                    result.addMessage("Não possui a quantidade de atributos esperada: " + expectedNumberOfAttributes + " ❌");

                for (Attribute attribute : criteriaByClass.getAttributes()) {
                    if (hasAttributeOfType(attribute.getName(), attribute.getType())) 
                        result.addMessage("O atributo " + attribute.getName() + " é do tipo " + attribute.getType() + " ✅");
                    else 
                        result.addMessage("Não há o atributo " + attribute.getName() + " ou não é do tipo " + attribute.getType() + " ❌");
                }
                break;
            }
        }

        if (!hasClass) {
            result.addMessage("A classe " + getClazzName() + " não foi encontrada nos critérios de correção. ❌");
        }

        return result;
    }
}


// // Método para verificar se todos os métodos são públicos
    // private boolean isAllMethodsPublic() {
    //     return Arrays.stream(methods)
    //         .allMatch(method -> Modifier.isPublic(method.getModifiers()));
    // }

    // // Método para verificar se a classe implementa todas as interfaces especificadas
    // private boolean implementsInterfaces(Class<?>... requiredInterfaces) {
    //     return Arrays.asList(interfaces).containsAll(Arrays.asList(requiredInterfaces));
    // }

    // // Método para verificar se um método específico existe e tem o número correto de parâmetros
    // private boolean hasMethodWithParameters(String methodName, int parameterCount) {
    //     return Arrays.stream(methods)
    //         .anyMatch(method -> method.getName().equals(methodName) && method.getParameterCount() == parameterCount);
    // }

    // // Método para verificar se a classe possui métodos `getters` e `setters` para um atributo específico
    // private boolean hasGettersAndSetters(String attributeName) {
    //     String capitalized = attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
    //     boolean hasGetter = Arrays.stream(methods)
    //         .anyMatch(method -> method.getName().equals("get" + capitalized) && method.getParameterCount() == 0);
    //     boolean hasSetter = Arrays.stream(methods)
    //         .anyMatch(method -> method.getName().equals("set" + capitalized) && method.getParameterCount() == 1);
    //     return hasGetter && hasSetter;
    // }

    // // Método para verificar se a classe estende uma superclasse específica
    // private boolean extendsSuperclass(Class<?> requiredSuperclass) {
    //     return superClass != null && superClass.equals(requiredSuperclass);
    // }

    // // Método para verificar se a classe implementa algum método da interface ou da superclasse
    // private boolean overridesMethodFromSuperclassOrInterface(String methodName) {
    //     return Arrays.stream(methods)
    //         .anyMatch(method -> method.getName().equals(methodName) && method.isAnnotationPresent(Override.class));
    // }

    // // Método para verificar se todos os métodos têm nomes que seguem convenções de nomenclatura (camelCase)
    // private boolean areAllMethodNamesCamelCase() {
    //     return Arrays.stream(methods)
    //         .allMatch(method -> Character.isLowerCase(method.getName().charAt(0)) &&
    //                             !method.getName().contains("_"));
    // }