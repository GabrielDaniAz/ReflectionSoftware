package com.reflectionsoftware.model.criteria;

import java.util.Map;
import com.google.gson.annotations.SerializedName;

/**
 * A classe {@code Constructor} representa um construtor de uma classe
 * dentro de um exercício, contendo informações sobre seus parâmetros
 * e a visibilidade do construtor.
 */
public class CriteriaConstructor {

    @SerializedName("parametros")
    private Map<String, String> parameters;

    @SerializedName("visibilidade")
    private String visibility;

    /**
     * Retorna os parâmetros do construtor.
     * 
     * @return um {@code Map<String, String>} contendo os parâmetros do construtor,
     * ou um mapa vazio se os parâmetros forem nulos.
     */
    public Map<String, String> getParameters() {
        return (parameters != null) ? parameters : Map.of();
    }

    /**
     * Retorna a visibilidade do construtor.
     * 
     * @return uma {@code String} representando a visibilidade do construtor,
     * ou "public" se a visibilidade for nula.
     */
    public String getVisibility() {
        return (visibility != null) ? visibility : "public";
    }

    /**
     * Retorna uma representação em formato JSON desta instância de {@code Constructor}.
     * 
     * @return uma {@code String} contendo a representação JSON do construtor.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"parametros\": ").append(getParameters()).append(",");
        sb.append("\"visibilidade\": \"").append(getVisibility()).append("\"");
        sb.append("}");
        return sb.toString();
    }
}
