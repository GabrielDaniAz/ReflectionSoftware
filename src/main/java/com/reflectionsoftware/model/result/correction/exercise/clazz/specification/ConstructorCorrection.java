package com.reflectionsoftware.model.result.correction.exercise.clazz.specification;

import com.reflectionsoftware.model.clazz.specification.ConstructorInfo;

public class ConstructorCorrection {
    private ConstructorInfo templateConstructor;
    private ConstructorInfo studentConstructor;

    private boolean exists;

    private final boolean isVisibilityCorrect;
    private final boolean isModifiersCorrect;
    private final boolean isNameCorrect;
    private final boolean isParameterTypesCorrect;

    public ConstructorCorrection(ConstructorInfo templateConstructor, ConstructorInfo studentConstructor, 
            boolean isVisibilityCorrect, boolean isModifiersCorrect, boolean isNameCorrect, boolean isParameterTypesCorrect) {
                
        this.templateConstructor = templateConstructor;
        this.studentConstructor = studentConstructor;

        this.exists = true;

        this.isVisibilityCorrect = isVisibilityCorrect;
        this.isModifiersCorrect = isModifiersCorrect;
        this.isNameCorrect = isNameCorrect;
        this.isParameterTypesCorrect = isParameterTypesCorrect;
    }

    public ConstructorCorrection(ConstructorInfo templateConstructor) {
        this.templateConstructor = templateConstructor;
        this.exists = false;

        this.studentConstructor = null;

        this.isVisibilityCorrect = false;
        this.isModifiersCorrect = false;
        this.isNameCorrect = false;
        this.isParameterTypesCorrect = false;
    }

    public ConstructorInfo getStudentConstructor() { return studentConstructor; }
    public ConstructorInfo getTemplateConstructor() { return templateConstructor; }

    public boolean exists() { return exists; }

    public boolean isVisibilityCorrect() { return isVisibilityCorrect; }
    public boolean isModifiersCorrect() { return isModifiersCorrect; }
    public boolean isNameCorrect() { return isNameCorrect; }
    public boolean isParameterTypesCorrect() { return isParameterTypesCorrect; }

    public int getGrade() {
        int score = 0;
    
        if (isVisibilityCorrect) score += 1;
        if (isModifiersCorrect) score += 1;
        if (isNameCorrect) score += 1;
        if (isParameterTypesCorrect) score += 1;
    
        return (score*100)/4;
    }
    

    @Override
    public String toString() {
        return String.format(
            "Visibilidade: %s, Modificador: %s, Nome: %s, Parâmetros: %s",
            isVisibilityCorrect ? "Correto" : "Incorreto",
            isModifiersCorrect ? "Correto" : "Incorreto",
            isNameCorrect ? "Correto" : "Incorreto",
            isParameterTypesCorrect ? "Correto" : "Incorreto"
        );
    }
}
