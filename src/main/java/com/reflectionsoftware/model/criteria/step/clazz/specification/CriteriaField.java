package com.reflectionsoftware.model.criteria.step.clazz.specification;

import com.google.gson.annotations.SerializedName;

public class CriteriaField {
    @SerializedName("nome")
    private String name;

    @SerializedName("tipo")
    private String type;

    @SerializedName("final")
    private Boolean isFinal;

    public CriteriaField() {
    }

    public String getName() {
        return (name != null) ? name : "Atributo não especificado";
    }

    public String getType() {
        return (type != null) ? type : "Tipo não especificado";
    }

    public boolean isFinal() {
        return (isFinal != null) ? isFinal : false;
    }

    @Override
    public String toString() {
        return "{ \"nome\": \"" + getName() + "\", " +
            "\"tipo\": \"" + getType() + "\", " +
            "\"final\": " + isFinal() + " }";
    }
}
