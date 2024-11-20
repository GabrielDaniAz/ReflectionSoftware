package com.reflectionsoftware.model;

import java.util.List;

import com.reflectionsoftware.model.clazz.ClassInfo;
import com.reflectionsoftware.model.result.correction.ReflectionResult;

public class Student {
    private final String name;
    private final List<ClassInfo> classes;
    private ReflectionResult reflectionResult;

    public Student(String name, List<ClassInfo> classes) {
        this.name = name;
        this.classes = classes;
    }

    public String getName() { return name; }
    public List<ClassInfo> getClasses() { return classes; }
    public ReflectionResult getReflectionResult() { return reflectionResult; }
    public void setReflectionResult(ReflectionResult reflectionResult) { this.reflectionResult = reflectionResult; }
}
