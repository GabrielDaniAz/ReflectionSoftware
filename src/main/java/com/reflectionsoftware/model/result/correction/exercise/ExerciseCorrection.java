package com.reflectionsoftware.model.result.correction.exercise;

import java.util.ArrayList;
import java.util.List;

import com.reflectionsoftware.model.result.correction.exercise.clazz.ClassCorrection;

public class ExerciseCorrection {
    
    private String exerciseName;
    private List<ClassCorrection> classCorrections;

    public ExerciseCorrection(String exerciseName) {
        this.exerciseName = exerciseName;

        this.classCorrections = new ArrayList<>();
    }

    public String getExerciseName() { return exerciseName; }
    public List<ClassCorrection> getClassCorrections() { return classCorrections; }

    public List<String> getMissingClasses() {
        List<String> missingClasses = new ArrayList<>();

        for (ClassCorrection classCorrection : classCorrections) {
            if(classCorrection.getStudentClass() == null) {
                missingClasses.add(classCorrection.getTemplateClass().getSimpleName());
            }
        }

        return missingClasses;
    }
    
    public void addClassCorrection(ClassCorrection classCorrection) { this.classCorrections.add(classCorrection); }

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
        if (getMissingClasses().isEmpty()) {
            sb.append("  Nenhuma classe ausente.\n");
        } else {
            for (String missingClass : getMissingClasses()) {
                sb.append("  - ").append(missingClass).append("\n");
            }
        }
    
        return sb.toString();
    }
}
