package com.reflectionsoftware.model.result.correction;

import java.util.List;

import com.reflectionsoftware.model.result.correction.exercise.ExerciseCorrection;


public class ReflectionResult {
    private List<ExerciseCorrection> exerciseCorrections;

    public ReflectionResult(List<ExerciseCorrection> exerciseCorrections){
        this.exerciseCorrections = exerciseCorrections;
    }

    public List<ExerciseCorrection> getExerciseCorrections() { return exerciseCorrections; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Resultado:\n");

        if (exerciseCorrections.isEmpty()) {
            sb.append("  Nenhum exercício corrigido.\n");
        } else {
            for (ExerciseCorrection correction : exerciseCorrections) {
                sb.append(correction.toString()).append("\n");
            }
        }

        return sb.toString();
    }

}
