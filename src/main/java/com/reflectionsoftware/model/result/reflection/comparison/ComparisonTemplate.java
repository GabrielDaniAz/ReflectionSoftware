package com.reflectionsoftware.model.result.reflection.comparison;

import java.util.List;

public class ComparisonTemplate {
    private final String exerciseName;
    private final List<ComparisonClass> comparisonClasses;
    private final List<String> missingClasses;

    public ComparisonTemplate(String exerciseName, List<ComparisonClass> comparisonClasses, List<String> missingClasses) { 
        this.exerciseName = exerciseName;
        this.comparisonClasses = comparisonClasses;
        this.missingClasses = missingClasses;
    }
    
    public String getExerciseName() { return exerciseName; }
    public List<ComparisonClass> getComparisonClasses() { return comparisonClasses; }
    public List<String> getMissingClasses() { return missingClasses; }
}
