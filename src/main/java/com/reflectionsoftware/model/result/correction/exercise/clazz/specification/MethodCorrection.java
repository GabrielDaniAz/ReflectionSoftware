package com.reflectionsoftware.model.result.correction.exercise.clazz.specification;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodCorrection {
    private final Method templateMethod;
    private final Method studentMethod;

    private boolean isVisibilityCorrect;
    private boolean isModifiersCorrect;
    private boolean isReturnTypeCorrect;
    private boolean isParameterTypesCorrect;

    private double totalGrade;

    public MethodCorrection(Method templateMethod, Method studentMethod) {
        this.templateMethod = templateMethod;
        this.studentMethod = studentMethod;
    }

    // Getters
    public Method getTemplateMethod() { return templateMethod; }
    public Method getStudentMethod() { return studentMethod; }

    public boolean isVisibilityCorrect() { return isVisibilityCorrect; }
    public boolean isModifiersCorrect() { return isModifiersCorrect; }
    public boolean isReturnTypeCorrect() { return isReturnTypeCorrect; }
    public boolean isParameterTypesCorrect() { return isParameterTypesCorrect; }

    public double getTotalGrade() { return totalGrade; }

    public boolean exists() { return studentMethod != null; }

    // Setters
    public void setVisibilityCorrect(boolean isCorrect) { this.isVisibilityCorrect = isCorrect; }
    public void setModifiersCorrect(boolean isCorrect) { this.isModifiersCorrect = isCorrect; }
    public void setReturnTypeCorrect(boolean isCorrect) { this.isReturnTypeCorrect = isCorrect; }
    public void setParameterTypesCorrect(boolean isCorrect) { this.isParameterTypesCorrect = isCorrect; }
    public void setTotalGrade(double totalGrade) { this.totalGrade = totalGrade; }

    // Method to calculate grade
    public int getGrade() {
        int score = 0;
    
        if (isVisibilityCorrect) score += 1;
        if (isModifiersCorrect) score += 1;
        if (isReturnTypeCorrect) score += 1;
        if (isParameterTypesCorrect) score += 1;
    
        return (score * 100) / 4;
    }

    public String templateString() {
        return methodToString(templateMethod);
    }

    public String studentString() {
        return methodToString(studentMethod);
    }

    private String methodToString(Method method) {
        if (method == null) {
            return "Método não definido";
        }

        // Obter modificadores como string (ex: public, static)
        String modifiers = Modifier.toString(method.getModifiers());
        // Obter tipo de retorno
        String returnType = method.getReturnType().getSimpleName();
        // Obter nome do método
        String methodName = method.getName();
        // Obter tipos de parâmetros
        String parameters = Stream.of(method.getParameterTypes())
                                .map(Class::getSimpleName)
                                .collect(Collectors.joining(", "));

        // Combinar tudo no formato desejado
        return String.format("%s %s %s(%s)", modifiers, returnType, methodName, parameters);
    }


    // Detailed string representation
    @Override
    public String toString() {
        return String.format(
            "Método: %s\n" +
            "  Visibilidade: %s\n" +
            "  Modificadores: %s\n" +
            "  Tipo de Retorno: %s\n" +
            "  Parâmetros: %s\n" +
            "  Nota Total: %.2f\n" +
            "  Nota Obtida: %.2f",
            templateMethod.getName(),
            isVisibilityCorrect ? "Correto" : "Incorreto",
            isModifiersCorrect ? "Correto" : "Incorreto",
            isReturnTypeCorrect ? "Correto" : "Incorreto",
            isParameterTypesCorrect ? "Correto" : "Incorreto",
            totalGrade,
            gradeObtained()
        );
    }

    // Calculate the obtained grade based on the total grade and percentile
    public double gradeObtained() {
        return (getGrade() * totalGrade) / 100.0;
    }
}
