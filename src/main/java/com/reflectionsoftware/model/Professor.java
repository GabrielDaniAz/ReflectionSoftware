package com.reflectionsoftware.model;

import com.reflectionsoftware.model.criteria.Criteria;
import com.reflectionsoftware.model.result.compilation.CompilationResult;

import java.util.List;

public class Professor{

    private static final String name = "Julio";
    private List<CompilationResult> compilationResults;
    private List<Template> templates;
    private Criteria criteria;

    public Professor(List<CompilationResult> compilationResults, List<Template> templates, Criteria criteria) {
        this.compilationResults = compilationResults;
        this.templates = templates;
        this.criteria = criteria;
    }

    public String getName(){ return name; }
    public List<CompilationResult> getCompilationResults(){ return compilationResults; }
    public List<Template> getTemplates() { return templates; }
    public Criteria getCriteria(){ return criteria; }
}
