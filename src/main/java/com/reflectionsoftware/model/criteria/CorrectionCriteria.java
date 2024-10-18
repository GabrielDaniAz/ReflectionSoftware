package com.reflectionsoftware.model.criteria;

import java.util.List;

/**
 * Classe que representa os critérios de correção para um exercício.
 */
public class CorrectionCriteria {
    private String exercicio; // Nome do exercício
    private List<GeneralCriteria> criterios_gerais; // Lista de critérios gerais
    private List<CriteriaByClass> criterios_por_classe; // Lista de critérios por classe

    /**
     * Obtém o nome do exercício.
     *
     * @return O nome do exercício.
     */
    public String getExercise() {
        return exercicio;
    }

    /**
     * Define o nome do exercício.
     *
     * @param exercicio O nome a ser atribuído ao exercício.
     */
    public void setExercise(String exercicio) {
        this.exercicio = exercicio;
    }

    /**
     * Obtém a lista de critérios gerais.
     *
     * @return A lista de critérios gerais.
     */
    public List<GeneralCriteria> getGeneralCriterias() {
        return criterios_gerais;
    }

    /**
     * Define a lista de critérios gerais.
     *
     * @param generalCriterias A lista de critérios gerais a ser atribuída.
     */
    public void setGeneralCriterias(List<GeneralCriteria> generalCriterias) {
        this.criterios_gerais = generalCriterias;
    }

    /**
     * Obtém a lista de critérios por classe.
     *
     * @return A lista de critérios por classe.
     */
    public List<CriteriaByClass> getCriteriaByClasses() {
        return criterios_por_classe;
    }

    /**
     * Define a lista de critérios por classe.
     *
     * @param criteriaByClass A lista de critérios por classe a ser atribuída.
     */
    public void setCriteriasByClasses(List<CriteriaByClass> criteriaByClass) {
        this.criterios_por_classe = criteriaByClass;
    }

    /**
     * Exibe os detalhes do critério de correção, incluindo o exercício e os critérios.
     */
    public void getDetails() {
        System.out.println("Exercício: " + exercicio);
        System.out.println("Critérios Gerais: " + criterios_gerais);
        
        // Itera sobre os critérios por classe e exibe suas informações
        criterios_por_classe.forEach(criteria -> {
            System.out.println("Classe: " + criteria.getClassName());
            System.out.println("Número de Atributos: " + criteria.getNumberOfAttributes());
            if (criteria.getAttributes() != null) {
                criteria.getAttributes().forEach(attribute -> {
                    System.out.println("  Atributo: " + attribute.getName() + ", Tipo: " + attribute.getType());
                });
            }
        });
    }
}
