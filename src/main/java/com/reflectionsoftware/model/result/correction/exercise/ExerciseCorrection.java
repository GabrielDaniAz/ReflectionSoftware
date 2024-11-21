package com.reflectionsoftware.model.result.correction.exercise;

import java.util.List;

import com.reflectionsoftware.model.result.correction.exercise.clazz.ClassCorrection;

public class ExerciseCorrection {
    
    private String exerciseName;
    private List<ClassCorrection> classCorrections;
    private List<String> missingClasses;

    public ExerciseCorrection(String exerciseName, List<ClassCorrection> classCorrections, List<String> missingClasses) {
        this.exerciseName = exerciseName;
        this.classCorrections = classCorrections;
        this.missingClasses = missingClasses;
    }

    public String getExerciseName() { return exerciseName; }
    public List<ClassCorrection> getClassCorrections() { return classCorrections; }
    public List<String> getMissingClasses() { return missingClasses; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Exercício: ").append(exerciseName).append("\n");

        sb.append("Classes:\n");
        if (classCorrections.isEmpty()) {
            sb.append("  Nenhuma classe para correção.\n");
        } else {
            for (ClassCorrection correction : classCorrections) {
                sb.append("  - ").append(correction.toString()).append("\n");
            }
        }

        sb.append("Classes ausentes:\n");
        if (missingClasses.isEmpty()) {
            sb.append("  Nenhuma classe ausente.\n");
        } else {
            for (String missingClass : missingClasses) {
                sb.append("  - ").append(missingClass).append("\n");
            }
        }

        return sb.toString();
    }

}
