package com.reflectionsoftware.model.criteria;

import java.util.Map;
import com.google.gson.annotations.SerializedName;

/**
 * A classe {@code Method} representa um método de uma classe
 * dentro de um exercício, contendo informações sobre seu nome,
 * parâmetros, tipo de retorno e visibilidade.
 */
public class CriteriaMethod {

    @SerializedName("nome")
    private String name;

    @SerializedName("parametros")
    private Map<String, String> parameters;

    @SerializedName("retorno")
    private String returnType;

    @SerializedName("visibilidade")
    private String visibility;

    /**
     * Retorna o nome do método.
     * 
     * @return uma {@code String} contendo o nome do método,
     * ou "Método não definido" se o nome for nulo.
     */
    public String getName() {
        return (name != null) ? name : "Método não definido";
    }

    /**
     * Retorna os parâmetros do método.
     * 
     * @return um {@code Map<String, String>} contendo os parâmetros do método,
     * ou um mapa vazio se os parâmetros forem nulos.
     */
    public Map<String, String> getParameters() {
        return (parameters != null) ? parameters : Map.of();
    }

    /**
     * Retorna o tipo de retorno do método.
     * 
     * @return uma {@code String} representando o tipo de retorno do método,
     * ou "void" se o tipo de retorno for nulo.
     */
    public String getReturnType() {
        return (returnType != null) ? returnType : "void";
    }

    /**
     * Retorna a visibilidade do método.
     * 
     * @return uma {@code String} representando a visibilidade do método,
     * ou "public" se a visibilidade for nula.
     */
    public String getVisibility() {
        return (visibility != null) ? visibility : "public";
    }

    /**
     * Retorna uma representação em formato JSON desta instância de {@code Method}.
     * 
     * @return uma {@code String} contendo a representação JSON do método.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"nome\": \"").append(getName()).append("\",");
        sb.append("\"parametros\": ").append(getParameters()).append(",");
        sb.append("\"retorno\": \"").append(getReturnType()).append("\",");
        sb.append("\"visibilidade\": \"").append(getVisibility()).append("\"");
        sb.append("}");
        return sb.toString();
    }
}
