package com.reflectionsoftware.model.criteria;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.google.gson.annotations.SerializedName;

/**
 * A classe {@code CorrectionCriteria} representa os critérios de correção para um exercício
 * específico. Ela contém informações gerais sobre o exercício e uma lista de critérios
 * organizados por classe.
 * 
 * A deserialização dos dados do JSON utiliza a anotação {@link SerializedName} para associar
 * os campos do JSON aos atributos da classe.
 * 
 * Exemplo de JSON esperado:
 * {
 *   "exercicio": "sistema-de-email",
 *   "criterios_gerais": {...},
 *   "criterios_por_classe": {
 *     "passo_1": [...],
 *     "passo_2": [...]
 *   }
 * }
 */

public class CriteriaCorrection {

    @SerializedName("exercicio")
    private String exercise;

    @SerializedName("criterios_gerais")
    private GeneralCriteria generalCriteria;

    @SerializedName("criterios_por_classe")
    private Map<String, List<CriteriaByClass>> criteriaByClass;

    private String correctionStep;

    /**
     * Retorna o nome do exercício, ou um valor padrão caso seja nulo.
     *
     * @return uma {@code String} representando o nome do exercício, ou "Exercício Desconhecido"
     */
    public String getExercise() {
        return (exercise != null) ? exercise : "Exercício Desconhecido";
    }

    /**
     * Retorna os critérios gerais aplicáveis ao exercício, ou um objeto {@link GeneralCriteria}
     * padrão caso seja nulo.
     *
     * @return um objeto {@link GeneralCriteria} contendo os critérios gerais
     */
    public GeneralCriteria getGeneralCriteria() {
        return (generalCriteria != null) ? generalCriteria : new GeneralCriteria();
    }

    /**
     * Retorna o mapa de critérios organizados por classe e por passos, ou um mapa vazio caso seja nulo.
     *
     * @return um {@code Map<String, List<CriteriaByClass>>} contendo os critérios por classe
     */
    public Map<String, List<CriteriaByClass>> getCriteriaByClass() {
        return (criteriaByClass != null) ? criteriaByClass : Map.of();
    }

    /**
     * Retorna o passo de correção definido, ou "passo_1" caso seja nulo.
     *
     * @return uma {@code String} representando o passo de correção
     */
    public String getCorrectionStep() {
        return (correctionStep != null) ? correctionStep : "passo_1";
    }

    /**
     * Define o passo de correção e remove os critérios de classe que estão além do passo definido.
     *
     * @param correctionStep o passo de correção a ser definido
     */
    public void setCorrectionStep(String correctionStep) {
        this.correctionStep = correctionStep;
        if (criteriaByClass != null && correctionStep != null) {
            Iterator<String> iterator = criteriaByClass.keySet().iterator();
            while (iterator.hasNext()) {
                String step = iterator.next();
                if (step.compareTo(correctionStep) > 0) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"exercicio\": \"").append(getExercise()).append("\",");
        sb.append("\"criterios_gerais\": ").append(getGeneralCriteria()).append(",");
        sb.append("\"criterios_por_classe\": ").append(getCriteriaByClass());
        sb.append("}");
        return sb.toString();
    }
}
