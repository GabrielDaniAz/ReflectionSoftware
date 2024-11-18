package com.reflectionsoftware.model;

import java.util.List;

import com.reflectionsoftware.model.result.compilation.CompilationResult;
import com.reflectionsoftware.model.result.reflection.ReflectionResult;

public class Student {
    private final String name;
    private final CompilationResult compilationResult;
    private final List<Class<?>> classes;
    private ReflectionResult reflectionResult;

    public Student(String name, CompilationResult compilationResult, List<Class<?>> classes) {
        this.name = name;
        this.compilationResult = compilationResult;
        this.classes = classes;
    }

    public String getName() { return name; }
    public CompilationResult getCompilationResult() { return compilationResult; }
    public List<Class<?>> getClasses() { return classes; }
    public ReflectionResult getReflectionResult() { return reflectionResult; }
    public void setReflectionResult(ReflectionResult reflectionResult) { this.reflectionResult = reflectionResult; }
}
