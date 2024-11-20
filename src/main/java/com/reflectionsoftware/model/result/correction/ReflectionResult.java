package com.reflectionsoftware.model.result.correction;

import java.util.List;

import com.reflectionsoftware.model.result.correction.exercise.ExerciseCorrection;


public class ReflectionResult {
    private List<ExerciseCorrection> exerciseCorrections;

    public ReflectionResult(List<ExerciseCorrection> exerciseCorrections){
        this.exerciseCorrections = exerciseCorrections;
    }

    public List<ExerciseCorrection> getExerciseCorrections() { return exerciseCorrections; }
}
