package com.reflectionsoftware.model.result.correction.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.reflectionsoftware.model.result.correction.Correction;
import com.reflectionsoftware.model.result.correction.exercise.clazz.ClassCorrection;
import com.reflectionsoftware.model.template.exercise.Exercise;
import com.reflectionsoftware.util.reflection.ClassMapper;


public class ExerciseCorrection implements Correction {
    private final String exerciseName;
    private final List<ClassCorrection> classes;

    public ExerciseCorrection(Exercise exercise, List<Class<?>> studentClasses) {
        this.exerciseName = exercise.getExerciseName();
        this.classes = initializeClasses(exercise.getClasses(), studentClasses);
    }

    private List<ClassCorrection> initializeClasses(List<Class<?>> templateClasses, List<Class<?>> studentClasses) {
        HashMap<Class<?>, Class<?>> mappedClasses = ClassMapper.mapClasses(templateClasses, studentClasses);
        List<ClassCorrection> corrections = new ArrayList<>();
        mappedClasses.forEach((template, student) -> corrections.add(new ClassCorrection(template, student)));
        return corrections;
    }

    public String getExerciseName() { return exerciseName; }
    public List<ClassCorrection> getAllClasses() { return classes; }
    public List<ClassCorrection> getCorrectedClasses() {
        List<ClassCorrection> classesList = new ArrayList<>();
        for (ClassCorrection c : classes) {
            if(c.getTemplate() == null || c.getStudent() == null) continue;
            classesList.add(c);
        }
        return classesList;
    }
    public double getGrade() { return classes.stream().mapToDouble(ClassCorrection::getGrade).sum(); }
    public double getObtainedGrade() { return classes.stream().mapToDouble(ClassCorrection::getObtainedGrade).sum(); }

    public List<String> getMissingClasses() {
        List<String> missing = new ArrayList<>();

        for (ClassCorrection c : classes) {
            if(c.getStudent() == null){
                missing.add(c.getTemplate().getSimpleName());
            }
        }
        
        return missing;
    }
}
