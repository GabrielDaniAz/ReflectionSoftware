package com.reflectionsoftware.model;

import com.reflectionsoftware.model.criteria.Criteria;
import com.reflectionsoftware.service.compilation.CompilationService.CompilationResult;
import com.reflectionsoftware.service.compilation.CompilationService.Exercise;

import java.util.List;
import java.util.stream.Collectors;

public class Professor{

    private static final String name = "Julio";
    private List<CompilationResult> compilationResults;
    private Criteria criteria;

    public Professor(List<CompilationResult> compilationResults, Criteria criteria) {
        this.compilationResults = compilationResults;
        this.criteria = criteria;
    }

    public String getName(){ return name; }
    public List<CompilationResult> getCompilationResults(){ return compilationResults; }
    public Criteria getCriteria(){ return criteria; }
    public List<Exercise> getExercises() { return compilationResults.stream().map(CompilationResult::getExercise).collect(Collectors.toList()); }
}
