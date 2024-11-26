package com.reflectionsoftware.model.result.correction;

import java.util.ArrayList;
import java.util.List;

import com.reflectionsoftware.model.result.correction.exercise.ExerciseCorrection;

public class ReflectionResult {
    private final String templateName;
    private List<ExerciseCorrection> exerciseCorrections;

    public ReflectionResult(String templateName){
        this.templateName = templateName;

        this.exerciseCorrections = new ArrayList<>();
    }

    public String getTemplateName() { return templateName; }
    public List<ExerciseCorrection> getExerciseCorrections() { return exerciseCorrections; }
    public void addExerciseCorrection(ExerciseCorrection exerciseCorrection) { this.exerciseCorrections.add(exerciseCorrection); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resultado da Reflexão para o Template: ").append(templateName).append("\n");
        sb.append("Correções de Exercícios:\n");

        if (exerciseCorrections == null || exerciseCorrections.isEmpty()) {
            sb.append("  Nenhum exercício corrigido.\n");
        } else {
            for (ExerciseCorrection correction : exerciseCorrections) {
                sb.append("  ").append(correction).append("\n");
            }
        }

        return sb.toString();
    }
}
