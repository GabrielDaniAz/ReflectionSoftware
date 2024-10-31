package com.reflectionsoftware.model.criteria;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.reflectionsoftware.model.criteria.general.GeneralCriteria;
import com.reflectionsoftware.model.criteria.step.CriteriaStep;

public class Criteria {
    @SerializedName("exercicio")
    private String exercise;

    @SerializedName("criterios_gerais")
    private GeneralCriteria generalCriteria;

    @SerializedName("passos")
    private List<CriteriaStep> steps;

    public Criteria() {
    }

    public String getExercise() {
        return (exercise != null) ? exercise : "Exercicio não definido";
    }

    public GeneralCriteria getGeneralCriteria() {
        return (generalCriteria != null) ? generalCriteria : new GeneralCriteria();
    }

    public List<CriteriaStep> getSteps() {
        return (steps != null) ? steps : Collections.emptyList();
    }

    /**
     * Filtra os passos de correção e mantém apenas aqueles até o limite definido.
     *
     * @param untilStep o limite de passos a ser mantido
     */
    public void filterStepsUpTo(int untilStep) {
        if (steps != null) {
            Iterator<CriteriaStep> iterator = steps.iterator();
            while (iterator.hasNext()) {
                CriteriaStep step = iterator.next();
                if (step.getStep() > untilStep) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "{ \"exercicio\": \"" + getExercise() + "\", " +
            "\"criterios_gerais\": " + getGeneralCriteria().toString() + ", " +
            "\"passos\": " + getSteps().toString() + " }";
    }
}
