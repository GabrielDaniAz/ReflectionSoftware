package com.reflectionsoftware.model.criteria;

import com.google.gson.annotations.SerializedName;

/**
 * A classe {@code Attribute} representa um atributo de uma classe dentro
 * de um exercício, contendo informações sobre seu nome, tipo e se é final.
 */
public class CriteriaField {

    @SerializedName("nome")
    private String name;

    @SerializedName("tipo")
    private String type;

    @SerializedName("final")
    private Boolean isFinal;

    @SerializedName("static")
    private Boolean isStatic;

    /**
     * Retorna o nome do atributo.
     * 
     * @return o nome do atributo ou "Atributo não definido" se o nome for nulo.
     */
    public String getName() {
        return (name != null) ? name : "Atributo não definido";
    }

    /**
     * Retorna o tipo do atributo.
     * 
     * @return o tipo do atributo ou "Tipo não definido" se o tipo for nulo.
     */
    public String getType() {
        return (type != null) ? type : "Tipo não definido";
    }

    /**
     * Indica se o atributo é final.
     * 
     * @return {@code true} se o atributo for final; caso contrário, {@code false}.
     */
    public boolean isFinal() {
        return (isFinal != null) ? isFinal : false;
    }

    /**
     * Indica se o atributo é estático.
     * 
     * @return {@code true} se o atributo for static; caso contrário, {@code false}.
     */
    public boolean isStatic() {
        return (isStatic != null) ? isStatic : false;
    }

    /**
     * Retorna uma representação em formato JSON desta instância de {@code Attribute}.
     * 
     * @return uma {@code String} contendo a representação JSON do atributo.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"nome\": \"").append(getName()).append("\",");
        sb.append("\"tipo\": \"").append(getType()).append("\",");
        sb.append("\"final\": ").append(isFinal()).append("\",");
        sb.append("\"static\":").append(isStatic());
        sb.append("}");
        return sb.toString();
    }
}
