package com.reflectionsoftware.model;

import java.util.List;

public class Template {
    private final String exerciseName;
    private final List<Class<?>> classes;

    public Template(String exerciseName, List<Class<?>> classes){
        this.exerciseName = exerciseName;
        this.classes = classes;
    }

    public String getExerciseName(){ return exerciseName; }
    public List<Class<?>> getClasses(){ return classes; }
}
