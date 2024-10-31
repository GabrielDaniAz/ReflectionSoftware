package com.reflectionsoftware.model.criteria.step.clazz;

import com.google.gson.annotations.SerializedName;
import com.reflectionsoftware.model.criteria.step.clazz.specification.CriteriaConstructor;
import com.reflectionsoftware.model.criteria.step.clazz.specification.CriteriaField;
import com.reflectionsoftware.model.criteria.step.clazz.specification.CriteriaMethod;

public class CriteriaClazz {
    @SerializedName("nome_da_classe")
    private String clazzName;

    @SerializedName("especificacao_atributos")
    private String attributeSpecification;

    @SerializedName("especificacao_construtores")
    private String constructorSpecification;

    @SerializedName("especificacao_metodos")
    private String methodSpecification;

    @SerializedName("atributos")
    private CriteriaField[] fields;

    @SerializedName("construtores")
    private CriteriaConstructor[] constructors;

    @SerializedName("metodos")
    private CriteriaMethod[] methods;

    public CriteriaClazz() {
    }

    public String getClazzName() {
        return (clazzName != null) ? clazzName : "Classe não definida";
    }

    public String getAttributeSpecification() {
        return (attributeSpecification != null) ? attributeSpecification : "exata";
    }

    public String getConstructorSpecification() {
        return (constructorSpecification != null) ? constructorSpecification : "exata";
    }

    public String getMethodSpecification() {
        return (methodSpecification != null) ? methodSpecification : "exata";
    }

    public CriteriaField[] getFields(){
        return (fields != null) ? fields : new CriteriaField[0];
    }

    public CriteriaConstructor[] getConstructors() {
        return (constructors != null) ? constructors : new CriteriaConstructor[0];
    }
    
    public CriteriaMethod[] getMethods() {
        return (methods != null) ? methods : new CriteriaMethod[0];
    }

    @Override
    public String toString() {
        return "{ \"nome_da_classe\": \"" + getClazzName() + "\", " +
            "\"especificacao_atributos\": \"" + getAttributeSpecification() + "\", " +
            "\"especificacao_construtores\": \"" + getConstructorSpecification() + "\", " +
            "\"especificacao_metodos\": \"" + getMethodSpecification() + "\", " +
            "\"atributos\": " + java.util.Arrays.toString(getFields()) + ", " +
            "\"construtores\": " + java.util.Arrays.toString(getConstructors()) + ", " +
            "\"metodos\": " + java.util.Arrays.toString(getMethods()) + " }";
    }
}
