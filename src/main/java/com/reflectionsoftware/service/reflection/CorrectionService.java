package com.reflectionsoftware.service.reflection;

import java.util.List;

import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.model.result.correction.ReflectionResult;
import com.reflectionsoftware.model.result.correction.exercise.ExerciseCorrection;
import com.reflectionsoftware.model.result.correction.exercise.clazz.ClassCorrection;
import com.reflectionsoftware.model.template.Template;
import com.reflectionsoftware.model.template.exercise.Exercise;

public class CorrectionService {

    public ReflectionResult correct(Template template, Student student) {

        ReflectionResult reflectionResult = new ReflectionResult(template.getTemplateName());

        if(!student.getCompilationResult().isSuccess()){
            return reflectionResult;
        }

        for (Exercise exercise : template.getExercises()) {
            reflectionResult.addExerciseCorrection(correctExercise(exercise, student.getClasses()));
        }

        return reflectionResult;
    }

    private ExerciseCorrection correctExercise(Exercise exercise, List<Class<?>> studentClasses) {

        ExerciseCorrection exerciseCorrection = new ExerciseCorrection(exercise.getExerciseName());

        for (Class<?> clazz : exercise.getClasses()) {
            Class<?> studentClass = ComparisonUtils.getClassByName(clazz, studentClasses);
            exerciseCorrection.addClassCorrection(correctClass(clazz, studentClass));
        }

        return exerciseCorrection;
    }

    private ClassCorrection correctClass(Class<?> clazz, Class<?> studentClass) {

        ClassCorrection classCorrection = new ClassCorrection(clazz, studentClass);

        if(studentClass == null){
            return classCorrection;
        }

        ConstructorService.correctConstructors(clazz, studentClass, classCorrection);
        FieldService.correctFields(clazz, studentClass, classCorrection);
        MethodService.correctMethods(clazz, studentClass, classCorrection);

        return classCorrection;
    }
}
