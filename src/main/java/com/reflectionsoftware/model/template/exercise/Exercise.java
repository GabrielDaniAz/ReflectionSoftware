package com.reflectionsoftware.model.template.exercise;

import java.util.List;

public class Exercise {
    private final String name;
    private final List<Class<?>> classes;

    public Exercise(String name, List<Class<?>> classes) {
        this.name = name;
        this.classes = classes;
    }

    public String getExerciseName() { return name; }
    public List<Class<?>> getClasses() { return classes; }
}
