package com.reflectionsoftware.service.reflection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.reflectionsoftware.model.clazz.ClassInfo;
import com.reflectionsoftware.model.result.correction.ReflectionResult;
import com.reflectionsoftware.model.result.correction.exercise.ExerciseCorrection;
import com.reflectionsoftware.model.result.correction.exercise.clazz.ClassCorrection;
import com.reflectionsoftware.model.template.Exercise;
import com.reflectionsoftware.model.template.Template;

public class CorrectionService {

    public ReflectionResult correct(Template template, List<ClassInfo> studentClasses) {
        List<ExerciseCorrection> exerciseCorrections = template.getExercises().stream()
            .map(exercise -> correctExercise(exercise, studentClasses))
            .collect(Collectors.toList());

        return new ReflectionResult(exerciseCorrections);
    }

    private ExerciseCorrection correctExercise(Exercise templateExercise, List<ClassInfo> studentClasses) {
        List<ClassCorrection> classCorrections = new ArrayList<>();
        List<String> missingClasses = new ArrayList<>();

        for (ClassInfo templateClass : templateExercise.getClasses()) {
            Optional<ClassInfo> studentClass = ComparisonUtils.getClassByName(templateClass.getClassName(), studentClasses);
            if(!studentClass.isPresent()){
                missingClasses.add(templateClass.getClassName());
                continue;
            }
            classCorrections.add(compareClasses(templateClass, studentClass.get()));
        }

        return new ExerciseCorrection(templateExercise.getExerciseName(), classCorrections, missingClasses);
    }

    private ClassCorrection compareClasses(ClassInfo templateClass, ClassInfo studentClass) {
        return new ClassCorrection(
            studentClass.getClassName(),
            ConstructorService.compareConstructors(templateClass, studentClass),
            FieldService.compareFields(templateClass, studentClass),
            MethodService.compareMethods(templateClass, studentClass)
        );
    }
}
