package com.reflectionsoftware.model.result.correction.exercise.clazz.specification;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldCorrection {
    private final Field templateField;
    private final Field studentField;

    private boolean isVisibilityCorrect;
    private boolean isModifiersCorrect;
    private boolean isTypeCorrect;

    private double totalGrade;

    public FieldCorrection(Field templateField, Field studentField) {
        this.templateField = templateField;
        this.studentField = studentField;
    }

    // Getters
    public Field getTemplateField() { return templateField; }
    public Field getStudentField() { return studentField; }

    public String getFieldName() {
        return studentField != null ? studentField.getName() : templateField.getName();
    }

    public boolean isVisibilityCorrect() { return isVisibilityCorrect; }
    public boolean isModifiersCorrect() { return isModifiersCorrect; }
    public boolean isTypeCorrect() { return isTypeCorrect; }
    public double getTotalGrade() { return totalGrade; }
    public boolean exists() { return studentField != null; }

    // Setters
    public void setVisibilityCorrect(boolean isCorrect) { isVisibilityCorrect = isCorrect; }
    public void setModifiersCorrect(boolean isCorrect) { isModifiersCorrect = isCorrect; }
    public void setTypeCorrect(boolean isCorrect) { isTypeCorrect = isCorrect; }
    public void setTotalGrade(double totalGrade) { this.totalGrade = totalGrade; }

    // Nota percentual e final
    public int getPercentilGrade() {
        int score = 0;

        if (isVisibilityCorrect) score++;
        if (isModifiersCorrect) score++;
        if (isTypeCorrect) score++;

        return (score * 100) / 3;
    }

    public double gradeObtained() {
        return (getPercentilGrade() * totalGrade) / 100.0;
    }

    public String templateString() {
        return fieldToString(templateField);
    }
    
    public String studentString() {
        return fieldToString(studentField);
    }
    
    private String fieldToString(Field field) {
        if (field == null) {
            return "Campo não definido";
        }
    
        // Obter modificadores como string (ex: public, private)
        String modifiers = Modifier.toString(field.getModifiers());
        // Obter tipo do campo
        String fieldType = field.getType().getSimpleName();
        // Obter nome do campo
        String fieldName = field.getName();
    
        // Combinar tudo no formato desejado
        return String.format("%s %s %s", modifiers, fieldType, fieldName);
    }    

    // toString
    @Override
    public String toString() {
        return String.format(
            "Correção do Campo:\n" +
            "  Nome: %s\n" +
            "  Template Field: %s\n" +
            "  Student Field: %s\n" +
            "  Visibilidade: %s\n" +
            "  Modificadores: %s\n" +
            "  Tipo: %s\n" +
            "  Nota Total: %.2f\n" +
            "  Nota Obtida: %.2f\n",
            getFieldName(),
            templateField,
            studentField != null ? studentField : "Não existe",
            isVisibilityCorrect ? "Correta" : "Incorreta",
            isModifiersCorrect ? "Corretos" : "Incorretos",
            isTypeCorrect ? "Correto" : "Incorreto",
            totalGrade,
            gradeObtained()
        );
    }
}
