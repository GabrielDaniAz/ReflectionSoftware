package com.javacorrige.model.template;

import java.util.List;

import com.javacorrige.model.template.exercise.Exercise;

public class Template {
    private final String templateName;
    private List<Exercise> exercises;

    public Template(String templateName, List<Exercise> exercises) {
        this.templateName = templateName;
        this.exercises = exercises;
    }

    public String getTemplateName() { return templateName; }
    public List<Exercise> getExercises() { return exercises; }
}
