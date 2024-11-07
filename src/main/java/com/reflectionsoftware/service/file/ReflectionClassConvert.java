package com.reflectionsoftware.service.file;

import java.io.File;
import com.reflectionsoftware.model.criteria.Criteria;
import com.reflectionsoftware.model.criteria.step.CriteriaStep;
import com.reflectionsoftware.model.criteria.step.clazz.CriteriaClazz;

public class ReflectionClassConvert {

    public static void convertCriteriaToJava(Criteria criteria, String rootDirectory) {
        FileService.removeDirectoryIfExists(rootDirectory);
        FileService.createDirectoryIfNotExists(rootDirectory);

        for (CriteriaStep step : criteria.getSteps()) {
            String stepDirectory = rootDirectory + File.separator + step.getStep();
            FileService.createDirectoryIfNotExists(stepDirectory);

            for (CriteriaClazz clazz : step.getClazzes()) {
                String classFileName = stepDirectory + File.separator + clazz.getClazzName() + ".java";
                String classContent = generateClassContent(clazz);
                FileService.writeFile(classFileName, classContent);
            }
        }
    }

    private static String generateClassContent(CriteriaClazz clazz) {
        StringBuilder classContent = new StringBuilder();
        classContent.append("public class ").append(clazz.getClazzName()).append(" {\n");

        clazz.getFields().forEach(field -> {
            classContent.append("    private");
            String defaultValue = "";
            if (field.isFinal()) {
                classContent.append(" final");
                defaultValue = getDefaultValue(field.getType());
            }
            classContent.append(" ").append(field.getType()).append(" ").append(field.getName()).append(defaultValue).append(";\n");
        });
        classContent.append("\n");

        // Adiciona construtores e métodos
        addConstructorsAndMethods(clazz, classContent);
        classContent.append("}\n");
        return classContent.toString();
    }

    private static String getDefaultValue(String fieldType) {
        switch (fieldType) {
            case "String": return "= null";
            case "int": return "= 0";
            case "boolean": return "= false";
            case "double": return "= 0.0";
            default: return "= null";
        }
    }

    private static void addConstructorsAndMethods(CriteriaClazz clazz, StringBuilder classContent) {
        clazz.getConstructors().forEach(constructor -> {
            classContent.append("    ").append(constructor.getVisibility()).append(" ").append(clazz.getClazzName()).append("(");
            constructor.getParameters().forEach((name, type) -> classContent.append(type).append(" ").append(name).append(", "));
            if (!constructor.getParameters().isEmpty()) {
                classContent.setLength(classContent.length() - 2); // Remove última vírgula
            }
            classContent.append(") {\n");
            constructor.getParameters().keySet().forEach(param -> 
                classContent.append("       // this.").append(param).append(" = ").append(param).append(";\n")
            );
            classContent.append("    }\n\n");
        });

        clazz.getMethods().forEach(method -> {
            classContent.append("    ").append(method.getVisibility()).append(" ").append(method.getReturnType()).append(" ").append(method.getName()).append("(");
            method.getParameters().forEach((name, type) -> classContent.append(type).append(" ").append(name).append(", "));
            if (!method.getParameters().isEmpty()) {
                classContent.setLength(classContent.length() - 2);
            }
            classContent.append(") {\n");
            if ("void".equals(method.getReturnType())) {
                classContent.append("        // Implementar lógica do método aqui\n");
            } else {
                classContent.append("        return null; // Implementar lógica do método aqui\n");
            }
            classContent.append("    }\n\n");
        });
    }
}
