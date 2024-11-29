package com.reflectionsoftware.model.result.correction;

import java.util.List;
import java.util.stream.Collectors;
import com.reflectionsoftware.model.result.correction.exercise.ExerciseCorrection;
import com.reflectionsoftware.model.template.Template;

public class ReflectionResult implements Correction{
    private final String templateName;
    private final List<ExerciseCorrection> exercises;

    public ReflectionResult(Template template, List<Class<?>> studentClasses) {
        this.templateName = template.getTemplateName();
        this.exercises = initializeExercises(template, studentClasses);
    }

    private List<ExerciseCorrection> initializeExercises(Template template, List<Class<?>> studentClasses) {
        return template.getExercises().stream()
                .map(exercise -> new ExerciseCorrection(exercise, studentClasses))
                .collect(Collectors.toList());
    }

    public String getTemplateName() { return templateName; }
    public List<ExerciseCorrection> getExercises() { return exercises; }
    public double getGrade() { return exercises.stream().mapToDouble(ExerciseCorrection::getGrade).sum(); }
    public double getObtainedGrade() { return exercises.stream().mapToDouble(ExerciseCorrection::getObtainedGrade).sum(); }
}
