package com.reflectionsoftware.model.criteria;

import java.util.Collections;
import java.util.List;

/**
 * Classe que representa critérios de correção específicos para uma classe em particular.
 */
public class CriteriaByClass {
    private String nome_da_classe; // Nome da classe
    private Integer numero_de_atributos; // Número de atributos definidos
    private List<Attribute> atributos; // Lista de atributos da classe

    /**
     * Obtém o nome da classe.
     *
     * @return O nome da classe.
     */
    public String getClassName() {
        return nome_da_classe;
    }

    /**
     * Define o nome da classe.
     *
     * @param className O nome a ser atribuído à classe.
     */
    public void setClassName(String className) {
        this.nome_da_classe = className;
    }

    /**
     * Obtém o número de atributos.
     *
     * @return O número de atributos definidos, ou o tamanho da lista de atributos se não estiver definido.
     */
    public Integer getNumberOfAttributes() {
        // Retorna o número de atributos definido, se existir
        // Se não existir, retorna o tamanho de atributos, se não for nulo, ou 0
        return numero_de_atributos != null ? numero_de_atributos : (atributos != null ? atributos.size() : 0);
    }

    /**
     * Define o número de atributos.
     *
     * @param numberOfAttributes O número a ser atribuído aos atributos da classe.
     */
    public void setNumberOfAttributes(Integer numberOfAttributes) {
        this.numero_de_atributos = numberOfAttributes;
    }

    /**
     * Obtém a lista de atributos.
     *
     * @return A lista de atributos, ou uma lista vazia se for nula.
     */
    public List<Attribute> getAttributes() {
        // Retorna a lista de atributos ou uma lista vazia se for nula
        return atributos != null ? atributos : Collections.emptyList();
    }

    /**
     * Define a lista de atributos.
     *
     * @param attributes A lista de atributos a ser atribuída.
     */
    public void setAttributes(List<Attribute> attributes) {
        this.atributos = attributes;
    }
}
