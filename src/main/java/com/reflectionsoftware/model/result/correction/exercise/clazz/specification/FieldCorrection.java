package com.reflectionsoftware.model.result.correction.exercise.clazz.specification;

import com.reflectionsoftware.model.clazz.specification.FieldInfo;

public class FieldCorrection {
    private FieldInfo templateField;
    private FieldInfo studentField;

    private final String fieldName;

    private final boolean isVisibilityCorrect;
    private final boolean isModifiersCorrect;
    private final boolean isTypeCorrect;
    private final boolean isNameCorrect;

    private final boolean exists;

    public FieldCorrection(FieldInfo templateField, FieldInfo studentField, String fieldName, boolean isVisibilityCorrect, 
            boolean isModifiersCorrect, boolean isTypeCorrect, boolean isNameCorrect) {
        
        this.templateField = templateField;
        this.studentField = studentField;

        this.fieldName = fieldName;

        this.isVisibilityCorrect = isVisibilityCorrect;
        this.isModifiersCorrect = isModifiersCorrect;
        this.isTypeCorrect = isTypeCorrect;
        this.isNameCorrect = isNameCorrect;

        this.exists = true;
    }

    public FieldCorrection(FieldInfo templateField) {
        this.templateField = templateField;

        this.fieldName = templateField.getName();

        this.studentField = null;

        this.isVisibilityCorrect = false;
        this.isModifiersCorrect = false;
        this.isTypeCorrect = false;
        this.isNameCorrect = false;

        this.exists = false;
    }

    public FieldInfo getTemplatetField() { return templateField; }
    public FieldInfo getStudentField() { return studentField; }

    public String getFieldName() { return fieldName; }

    public boolean isVisibilityCorrect() { return isVisibilityCorrect; }
    public boolean isModifiersCorrect() { return isModifiersCorrect; }
    public boolean isTypeCorrect() { return isTypeCorrect; }
    public boolean isNameCorrect() { return isNameCorrect; }

    public boolean exists() { return exists; }

    public int getGrade() {
        int score = 0;
    
        if (isVisibilityCorrect) score += 1;
        if (isModifiersCorrect) score += 1;
        if (isTypeCorrect) score += 1;
        if (isNameCorrect) score += 1;
    
        return (score*100)/4;
    }

    @Override
    public String toString() {
        return String.format(
            "Visibilidade: %s, Modificador: %s, Tipo: %s, Nome: %s",
            isVisibilityCorrect ? "Correto" : "Incorreto",
            isModifiersCorrect ? "Correto" : "Incorreto",
            isTypeCorrect ? "Correto" : "Incorreto",
            isNameCorrect ? "Correto" : "Incorreto"
        );
    }
}
