package com.reflectionsoftware.model.result.correction.exercise.clazz.specification;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstructorCorrection {
    private final Constructor<?> templateConstructor;
    private final Constructor<?> studentConstructor;

    private boolean isVisibilityCorrect;
    private boolean isModifiersCorrect;
    private boolean isParameterTypesCorrect;

    private double totalGrade;

    public ConstructorCorrection(Constructor<?> templateConstructor, Constructor<?> studentConstructor) { 
        this.templateConstructor = templateConstructor;
        this.studentConstructor = studentConstructor;
    }

    // Getters
    public Constructor<?> getTemplateConstructor() { return templateConstructor; }
    public Constructor<?> getStudentConstructor() { return studentConstructor; }

    public boolean exists() { return studentConstructor != null; }
    public boolean isVisibilityCorrect() { return isVisibilityCorrect; }
    public boolean isModifiersCorrect() { return isModifiersCorrect; }
    public boolean isParameterTypesCorrect() { return isParameterTypesCorrect; }
    public double getTotalGrade() { return totalGrade; }

    // Setters
    public void setVisibilityCorrect(boolean isCorrect) { isVisibilityCorrect = isCorrect; }
    public void setModifiersCorrect(boolean isCorrect) { isModifiersCorrect = isCorrect; }
    public void setParameterTypesCorrect(boolean isCorrect) { isParameterTypesCorrect = isCorrect; }
    public void setTotalGrade(double totalGrade) { this.totalGrade = totalGrade; }

    // Cálculo de notas
    public double getPercentilGrade() {
        int score = 0;
        if (isVisibilityCorrect) score++;
        if (isModifiersCorrect) score++;
        if (isParameterTypesCorrect) score++;
        return (score * 100) / 3.0;
    }

    public double gradeObtained() { 
        return (getPercentilGrade() * totalGrade) / 100.0; 
    }

    public String templateString() {
        return constructorToString(templateConstructor);
    }

    public String studentString() {
        return constructorToString(studentConstructor);
    }

    private String constructorToString(Constructor<?> constructor) {
        if (constructor == null) {
            return "Construtor não definido";
        }

        // Obter modificadores como string (ex: public, private)
        String modifiers = Modifier.toString(constructor.getModifiers());
        // Obter nome da classe
        String className = constructor.getDeclaringClass().getSimpleName();
        // Obter tipos dos parâmetros
        String parameters = Stream.of(constructor.getParameterTypes())
                                  .map(Class::getSimpleName)
                                  .collect(Collectors.joining(", "));
        // Combinar tudo no formato desejado
        return String.format("%s %s(%s)", modifiers, className, parameters);
    }
    
    @Override
    public String toString() {
        return String.format(
            "Correção do Construtor:\n" +
            "  Template: %s\n" +
            "  Estudante: %s\n" +
            "  Visibilidade: %s\n" +
            "  Modificadores: %s\n" +
            "  Tipos dos Parâmetros: %s\n" +
            "  Nota Total: %.2f\n" +
            "  Nota Obtida: %.2f\n",
            templateConstructor != null ? templateConstructor.getName() : "Não definido",
            studentConstructor != null ? studentConstructor.getName() : "Não definido",
            isVisibilityCorrect ? "Correta" : "Incorreta",
            isModifiersCorrect ? "Corretos" : "Incorretos",
            isParameterTypesCorrect ? "Corretos" : "Incorretos",
            totalGrade,
            gradeObtained()
        );
    }
}
