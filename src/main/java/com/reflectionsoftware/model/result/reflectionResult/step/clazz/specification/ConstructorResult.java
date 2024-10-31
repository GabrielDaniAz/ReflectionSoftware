package com.reflectionsoftware.model.result.reflectionResult.step.clazz.specification;

public class ConstructorResult {

    private boolean constructorExists;
    private boolean paramLengthMatch;
    private boolean paramTypesMatch;
    private boolean paramNamesMatch;
    private boolean visibilityMatch;

    public ConstructorResult(boolean[] results) {
        this.constructorExists = results[0];
        this.paramLengthMatch = results[1];
        this.paramTypesMatch = results[2];
        this.paramNamesMatch = results[3];
        this.visibilityMatch = results[4];
    }

    public boolean isConstructorExists() {
        return constructorExists;
    }

    public boolean isParamLengthMatch() {
        return paramLengthMatch;
    }

    public boolean isParamTypesMatch() {
        return paramTypesMatch;
    }

    public boolean isParamNamesMatch() {
        return paramNamesMatch;
    }

    public boolean isVisibilityMatch() {
        return visibilityMatch;
    }

    @Override
    public String toString() {
        return "ConstructorResult{" +
                "constructorExists=" + constructorExists +
                ", paramLengthMatch=" + paramLengthMatch +
                ", paramTypesMatch=" + paramTypesMatch +
                ", paramNamesMatch=" + paramNamesMatch +
                ", visibilityMatch=" + visibilityMatch +
                '}';
    }
}
