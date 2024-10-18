package com.reflectionsoftware.model.criteria;

/**
 * Classe que representa um atributo de uma classe ou objeto.
 */
public class Attribute {
    private String nome; // Nome do atributo
    private String tipo; // Tipo do atributo

    /**
     * Obtém o nome do atributo.
     *
     * @return O nome do atributo.
     */
    public String getName() {
        return nome;
    }

    /**
     * Define o nome do atributo.
     *
     * @param name O nome a ser atribuído ao atributo.
     */
    public void setName(String name) {
        this.nome = name;
    }

    /**
     * Obtém o tipo do atributo.
     *
     * @return O tipo do atributo.
     */
    public String getType() {
        return tipo;
    }

    /**
     * Define o tipo do atributo.
     *
     * @param type O tipo a ser atribuído ao atributo.
     */
    public void setType(String type) {
        this.tipo = type;
    }
}
