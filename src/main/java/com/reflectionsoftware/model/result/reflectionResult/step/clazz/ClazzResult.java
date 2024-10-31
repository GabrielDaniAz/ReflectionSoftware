package com.reflectionsoftware.model.result.reflectionResult.step.clazz;

import java.util.Arrays;

import com.reflectionsoftware.model.result.reflectionResult.step.clazz.specification.ConstructorResult;
import com.reflectionsoftware.model.result.reflectionResult.step.clazz.specification.FieldResult;
import com.reflectionsoftware.model.result.reflectionResult.step.clazz.specification.MethodResult;

public class ClazzResult {

    private String clazzName;
    private boolean clazzExists;
    private ConstructorResult[] constructorResults;
    private FieldResult[] fieldResults;
    private MethodResult[] methodResults;

    public ClazzResult(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setConstructorResults(ConstructorResult[] constructorResults) {
        this.constructorResults = constructorResults;
    }

    public void setFieldResults(FieldResult[] fieldResults) {
        this.fieldResults = fieldResults;
    }

    public void setMethodResults(MethodResult[] methodResults) {
        this.methodResults = methodResults;
    }

    public ConstructorResult[] getConstructorResults() {
        return constructorResults;
    }

    public FieldResult[] getFieldResults() {
        return fieldResults;
    }

    public MethodResult[] getMethodResults() {
        return methodResults;
    }

    public boolean isClazzExists() {
        return clazzExists;
    }

    public void setClazzExists(boolean clazzExists) {
        this.clazzExists = clazzExists;
    }

    @Override
    public String toString() {
        return "ClazzResult{" +
                "clazzName='" + clazzName + '\'' +
                ", clazzExists=" + clazzExists +
                ", constructorResults=" + Arrays.toString(constructorResults) +
                ", fieldResults=" + Arrays.toString(fieldResults) +
                ", methodResults=" + Arrays.toString(methodResults) +
                '}';
    }
}
