package com.javacorrige.model;

import java.util.List;

import com.javacorrige.model.result.compilation.CompilationResult;
import com.javacorrige.model.result.correction.ReflectionResult;

public class Student {
    private final String name;
    private List<Class<?>> classes;
    private CompilationResult compilationResult;
    private ReflectionResult reflectionResult;

    public Student(String name, List<Class<?>> classes, CompilationResult compilationResult) {
        this.name = name;
        this.classes = classes;
        this.compilationResult = compilationResult;
    }

    public String getName() { return name; }
    public List<Class<?>> getClasses() { return classes; }
    public CompilationResult getCompilationResult() { return compilationResult; }
    public ReflectionResult getReflectionResult() { return reflectionResult; }
    
    public void setReflectionResult(ReflectionResult reflectionResult) { this.reflectionResult = reflectionResult; }
}
