package com.reflectionsoftware.model.result.reflectionResult.step.clazz.specification;

public class FieldResult {

    private String fieldName;
    private boolean fieldExists;
    private boolean typeMatch;
    private boolean privateMatch;
    private boolean finalMatch;

    public FieldResult(String fieldName, boolean[] results) {
        this.fieldName = fieldName;
        this.fieldExists = results[0];
        this.typeMatch = results[1];
        this.privateMatch = results[2];
        this.finalMatch = results[3];
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean doesFieldExist() {
        return fieldExists;
    }

    public boolean isTypeMatch() {
        return typeMatch;
    }

    public boolean isPrivateMatch() {
        return privateMatch;
    }

    public boolean isFinalMatch() {
        return finalMatch;
    }

    @Override
    public String toString() {
        return "FieldResult{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldExists=" + fieldExists +
                ", typeMatch=" + typeMatch +
                ", privateMatch=" + privateMatch +
                ", finalMatch=" + finalMatch +
                '}';
    }
}
