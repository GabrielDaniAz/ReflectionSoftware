package com.reflectionsoftware.model.template;

import java.util.List;

import com.reflectionsoftware.model.clazz.ClassInfo;

public class Exercise {
    private final String name;
    private final List<ClassInfo> classes;

    public Exercise(String name, List<ClassInfo> classes) {
        this.name = name;
        this.classes = classes;
    }

    public String getExerciseName() {
        return name;
    }

    public List<ClassInfo> getClasses() {
        return classes;
    }
}
