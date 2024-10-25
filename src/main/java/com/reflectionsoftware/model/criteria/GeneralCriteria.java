package com.reflectionsoftware.model.criteria;

import com.google.gson.annotations.SerializedName;

/**
 * A classe {@code GeneralCriteria} define critérios gerais aplicáveis ao exercício
 * como um todo, incluindo requisitos de atributos, construtores e similaridade de nomes.
 * <p>
 * A deserialização dos dados do JSON utiliza a anotação {@link SerializedName} para associar
 * os campos do JSON aos atributos da classe.
 * 
 * Exemplo de JSON esperado:
 * {
 *   "atributos_todos_privados": true,
 *   "construtores_obritagorios": true,
 *   "similaridade_nomes": 0.9
 * }
 */
public class GeneralCriteria {

    /**
     * Indica se todos os atributos devem ser privados.
     */
    @SerializedName("atributos_todos_privados")
    private Boolean isAllAttributesPrivate;

    /**
     * Indica se todos os construtores são obrigatórios.
     */
    @SerializedName("construtores_obritagorios")
    private Boolean isAllConstructorsRequired;

    /**
     * Define o limite mínimo de similaridade de nomes entre os métodos e atributos esperados e os submetidos.
     */
    @SerializedName("similaridade_nomes")
    private Double nameSimilarityThreshold;

     /**
     * Retorna se todos os atributos são privados.
     *
     * @return {@code true} se todos os atributos forem privados, {@code false} caso contrário
     */
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
        return "{" +
                "\"atributos_todos_privados\": " + isAllAttributesPrivate() +
                ", \"construtores_obritagorios\": " + isAllConstructorsRequired() +
                ", \"similaridade_nomes\": " + getNameSimilarityThreshold() +
                '}';
    }
}
