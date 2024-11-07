package com.reflectionsoftware.model;

import com.reflectionsoftware.service.compilation.CompilationService.CompilationResult;
import com.reflectionsoftware.service.reflection.CorrectionService.ReflectionResult;

public class Student {
    private String name;
    private CompilationResult compilationResult;
    private ReflectionResult reflectionResult;

    public Student(String name, CompilationResult compilationResult) {
        this.name = name;
        this.compilationResult = compilationResult;
    }

    public String getName() { return name; }
    public CompilationResult getCompilationResult() { return compilationResult; }
    public ReflectionResult getReflectionResult() { return reflectionResult; }
    public void setReflectionResult(ReflectionResult reflectionResult) { this.reflectionResult = reflectionResult; }
}
