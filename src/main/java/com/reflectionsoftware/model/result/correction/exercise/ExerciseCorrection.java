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
}
