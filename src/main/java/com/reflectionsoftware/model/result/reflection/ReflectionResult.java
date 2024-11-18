package com.reflectionsoftware.model.result.reflection;

import java.util.List;

import com.reflectionsoftware.model.result.reflection.comparison.ComparisonTemplate;

public class ReflectionResult {
    private final List<ComparisonTemplate> exercises;

    public ReflectionResult(List<ComparisonTemplate> exercises){
        this.exercises = exercises;
    }

    public List<ComparisonTemplate> getExercises() { return exercises; }
}
