package com.reflectionsoftware.model.criteria.general;

import com.google.gson.annotations.SerializedName;

public class GeneralCriteria {

    @SerializedName("atributos_todos_privados")
    private Boolean isAllAttributesPrivate;

    @SerializedName("construtores_obrigatorios")
    private Boolean isAllConstructorsRequired;

    @SerializedName("similaridade_nomes")
    private Double nameSimilarityThreshold;

    public boolean isAllAttributesPrivate() {
        return (isAllAttributesPrivate != null) ? isAllAttributesPrivate : false;
    }

    /**
     * Retorna se todos os construtores são obrigatórios.
     *
     * @return {@code true} se todos os construtores forem obrigatórios, {@code false} caso contrário
     */
    public boolean isAllConstructorsRequired() {
        return (isAllConstructorsRequired != null) ? isAllConstructorsRequired : false;
    }

    /**
     * Retorna o limiar de similaridade de nomes.
     *
     * @return o limiar de similaridade de nomes como {@code double}
     */
    public double getNameSimilarityThreshold() {
        return (nameSimilarityThreshold != null) ? nameSimilarityThreshold : 0.0;
    }

    @Override
    public String toString() {
        return "{ \"atributos_todos_privados\": " + isAllAttributesPrivate() + ", " +
            "\"construtores_obrigatorios\": " + isAllConstructorsRequired() + ", " +
            "\"similaridade_nomes\": " + getNameSimilarityThreshold() + " }";
    }
}
