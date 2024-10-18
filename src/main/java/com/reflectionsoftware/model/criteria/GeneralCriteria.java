package com.reflectionsoftware.model.criteria;

/**
 * Enumeração que representa os critérios gerais de correção para as classes de código.
 */
public enum GeneralCriteria {
    /**
     * Indica que todos os atributos da classe devem ser privados.
     */
    ATRIBUTOS_TODOS_PRIVADOS,

    /**
     * Indica que a classe deve ter construtores obrigatórios.
     */
    CONSTRUTORES_OBRIGATORIOS,

    /**
     * Indica que todos os métodos da classe devem ser públicos.
     */
    METODOS_PUBLICOS,
}
