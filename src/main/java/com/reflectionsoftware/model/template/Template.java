package com.reflectionsoftware.model.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.reflectionsoftware.model.clazz.ClassInfo;

public class Template {
    private final List<Exercise> exercises;

    public Template(Map<String, List<ClassInfo>> exercisesMap) {
        this.exercises = new ArrayList<>();
        for (Map.Entry<String, List<ClassInfo>> entry : exercisesMap.entrySet()) {
            String exerciseName = entry.getKey();
            List<ClassInfo> classes = entry.getValue();
            exercises.add(new Exercise(exerciseName, classes));
        }
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
