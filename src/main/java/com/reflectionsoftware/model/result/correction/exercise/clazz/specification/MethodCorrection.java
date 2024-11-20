package com.reflectionsoftware.model.result.correction.exercise.clazz.specification;

import com.reflectionsoftware.model.clazz.specification.MethodInfo;

public class MethodCorrection {
    private MethodInfo templateMethod;
    private MethodInfo studentMethod;

    private String methodName;

    private final boolean isVisibilityCorrect;
    private final boolean isModifiersCorrect;
    private final boolean isReturnTypeCorrect;
    private final boolean isNameCorrect;
    private final boolean isParameterTypesCorrect;

    private final boolean exists;

    public MethodCorrection(MethodInfo templateMethod, MethodInfo studentMethod, String methodName, boolean isVisibilityCorrect, boolean isModifiersCorrect, 
        boolean isReturnTypeCorrect, boolean isNameCorrect, boolean isParameterTypesCorrect) {
            
        this.templateMethod = templateMethod;
        this.studentMethod = studentMethod;

        this.methodName = methodName;

        this.isVisibilityCorrect = isVisibilityCorrect;
        this.isModifiersCorrect = isModifiersCorrect;
        this.isReturnTypeCorrect = isReturnTypeCorrect;
        this.isNameCorrect = isNameCorrect;
        this.isParameterTypesCorrect = isParameterTypesCorrect;

        this.exists = true;
    }

    public MethodCorrection(MethodInfo templateMethod) {
        this.templateMethod = templateMethod;
        this.studentMethod = null;

        this.methodName = templateMethod.getName();

        this.isVisibilityCorrect = false;
        this.isModifiersCorrect = false;
        this.isReturnTypeCorrect = false;
        this.isNameCorrect = false;
        this.isParameterTypesCorrect = false;

        this.exists = false;
    }

    public MethodInfo getTemplateMethod() { return templateMethod; }
    public MethodInfo getStudentMethod() { return studentMethod; }

    public String getMethodName() { return methodName; }

    public boolean isVisibilityCorrect() { return isVisibilityCorrect; }
    public boolean isModifiersCorrect() { return isModifiersCorrect; }
    public boolean isReturnTypeCorrect() { return isReturnTypeCorrect; }
    public boolean isNameCorrect() { return isNameCorrect; }
    public boolean isParameterTypesCorrect() { return isParameterTypesCorrect; }

    public boolean exists() { return exists; }

    public int getGrade() {
        int score = 0;
    
        if (isVisibilityCorrect) score += 1;
        if (isModifiersCorrect) score += 1;
        if (isReturnTypeCorrect) score += 1;
        if (isNameCorrect) score += 1;
        if (isParameterTypesCorrect) score += 1;
    
        return (score*100)/5;
    }

    @Override
    public String toString() {
        return String.format(
            "Visibilidade: %s, Modificador: %s, Retorno: %s, Nome: %s, Parâmetros: %s",
            isVisibilityCorrect ? "Correto" : "Incorreto",
            isModifiersCorrect ? "Correto" : "Incorreto",
            isReturnTypeCorrect ? "Correto" : "Incorreto",
            isNameCorrect ? "Correto" : "Incorreto",
            isParameterTypesCorrect ? "Correto" : "Incorreto"
        );
    }
}
