package com.reflectionsoftware.model.criteria.step.clazz.specification;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.Map;

public class CriteriaMethod {
    @SerializedName("nome")
    private String name;

    @SerializedName("parametros")
    private Map<String, String> parameters;

    @SerializedName("retorno")
    private String returnType;

    @SerializedName("visibilidade")
    private String visibility;

    @SerializedName("valido_ate_passo")
    private Integer validUntilStep;

    public CriteriaMethod() {
    }

    public String getName() {
        return (name != null) ? name : "Método não especificado";
    }

    public Map<String, String> getParameters() {
        return (parameters != null) ? parameters : Collections.emptyMap();
    }

    public Class<?>[] getParameterTypes() {
        return parameters.values().stream()
                .map(this::getClassByName)
                .toArray(Class<?>[]::new);
    }

    public String[] getParameterNames() {
        return parameters != null ? parameters.keySet().toArray(new String[0]) : new String[0];
    }

    private Class<?> getClassByName(String className) {
        try {
            switch (className) {
                case "int":
                    return int.class;
                case "boolean":
                    return boolean.class;
                case "byte":
                    return byte.class;
                case "short":
                    return short.class;
                case "long":
                    return long.class;
                case "float":
                    return float.class;
                case "double":
                    return double.class;
                case "void":
                    return void.class;
                case "String":
                    return String.class;
                default:
                    return Class.forName(className);
            }
        } catch (ClassNotFoundException e) {
            return null; // Lidar com a exceção conforme necessário
        }
    }
    

    public String getReturnType() {
        return (returnType != null) ? returnType : "void";
    }

    public String getVisibility() {
        return (visibility != null) ? visibility : "public";
    }

    public Integer getValidUntilStep() {
        return (validUntilStep != null) ? validUntilStep : null; // Verificar isso depois
    }

    @Override
    public String toString() {
        return "{ \"nome\": \"" + getName() + "\", " +
            "\"parametros\": " + getParameters().toString() + ", " +
            "\"retorno\": \"" + getReturnType() + "\", " +
            "\"visibilidade\": \"" + getVisibility() + "\", " +
            "\"valido_ate_passo\": " + (getValidUntilStep() != null ? getValidUntilStep() : "null") + " }";
    }
}
