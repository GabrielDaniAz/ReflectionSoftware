package com.reflectionsoftware.model.result.reflectionResult.step.clazz.specification;

public class MethodResult {

    private String methodName;
    private boolean methodExists;
    private boolean paramLengthMatch;
    private boolean paramTypesMatch;
    private boolean returnTypeMatch;
    private boolean visibilityMatch;

    public MethodResult(String methodName, boolean[] results) {
        this.methodName = methodName;
        this.methodExists = results[0];
        this.paramLengthMatch = results[1];
        this.paramTypesMatch = results[2];
        this.returnTypeMatch = results[3];
        this.visibilityMatch = results[4];
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean isMethodExists() {
        return methodExists;
    }

    public boolean isParamLengthMatch() {
        return paramLengthMatch;
    }

    public boolean isParamTypesMatch() {
        return paramTypesMatch;
    }

    public boolean isReturnTypeMatch() {
        return returnTypeMatch;
    }

    public boolean isVisibilityMatch() {
        return visibilityMatch;
    }

    @Override
    public String toString() {
        return "MethodResult{" +
                "methodName='" + methodName + '\'' +
                ", methodExists=" + methodExists +
                ", paramLengthMatch=" + paramLengthMatch +
                ", paramTypesMatch=" + paramTypesMatch +
                ", returnTypeMatch=" + returnTypeMatch +
                ", visibilityMatch=" + visibilityMatch +
                '}';
    }
}
