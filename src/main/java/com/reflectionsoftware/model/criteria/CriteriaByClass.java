package com.reflectionsoftware.model.criteria;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * A classe {@code CriteriaByClass} representa os critérios de correção
 * para uma classe específica dentro de um exercício.
 * Ela contém informações sobre a classe, suas especificações de atributos,
 * construtores e métodos, além de listas desses elementos.
 */
public class CriteriaByClass {

    @SerializedName("nome_da_classe")
    private String className;

    @SerializedName("especificacao_atributos")
    private String attributesSpecification;

    @SerializedName("especificacao_construtores")
    private String constructorsSpecification;

    @SerializedName("especificacao_metodos")
    private String methodsSpecification;

    @SerializedName("atributos")
    private List<CriteriaField> attributes;

    @SerializedName("construtores")
    private List<CriteriaConstructor> constructors;

    @SerializedName("metodos")
    private List<CriteriaMethod> methods;

    /**
     * Retorna o nome da classe.
     * 
     * @return o nome da classe ou "Classe não especificada" se o nome for nulo.
     */
    public String getClassName() {
        return (className != null) ? className : "Classe não especificada";
    }

    /**
     * Retorna a especificação dos atributos.
     * 
     * @return a especificação de atributos ou "minima" se a especificação for nula.
     */
    public String getAttributesSpecification() {
        return (attributesSpecification != null) ? attributesSpecification : "minima";
    }

    /**
     * Retorna a especificação dos construtores.
     * 
     * @return a especificação de construtores ou "minima" se a especificação for nula.
     */
    public String getConstructorsSpecification() {
        return (constructorsSpecification != null) ? constructorsSpecification : "minima";
    }

    /**
     * Retorna a especificação dos métodos.
     * 
     * @return a especificação de métodos ou "minima" se a especificação for nula.
     */
    public String getMethodsSpecification() {
        return (methodsSpecification != null) ? methodsSpecification : "minima";
    }

    /**
     * Retorna a lista de atributos.
     * 
     * @return a lista de atributos ou uma lista vazia se a lista for nula.
     */
    public List<CriteriaField> getAttributes() {
        return (attributes != null) ? attributes : new ArrayList<>();
    }

    /**
     * Retorna a lista de construtores.
     * 
     * @return a lista de construtores ou uma lista vazia se a lista for nula.
     */
    public List<CriteriaConstructor> getConstructors() {
        return (constructors != null) ? constructors : new ArrayList<>();
    }

    /**
     * Retorna a lista de métodos.
     * 
     * @return a lista de métodos ou uma lista vazia se a lista for nula.
     */
    public List<CriteriaMethod> getMethods() {
        return (methods != null) ? methods : new ArrayList<>();
    }

    /**
     * Retorna uma representação em formato JSON desta instância de {@code CriteriaByClass}.
     * 
     * @return uma {@code String} contendo a representação JSON dos critérios da classe.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"nome_da_classe\": \"").append(getClassName()).append("\",");
        sb.append("\"especificacao_atributos\": \"").append(getAttributesSpecification()).append("\",");
        sb.append("\"especificacao_construtores\": \"").append(getConstructorsSpecification()).append("\",");
        sb.append("\"especificacao_metodos\": \"").append(getMethodsSpecification()).append("\",");
        sb.append("\"atributos\": ").append(getAttributes()).append(",");
        sb.append("\"construtores\": ").append(getConstructors()).append(",");
        sb.append("\"metodos\": ").append(getMethods());
        sb.append("}");
        return sb.toString();
    }
}
