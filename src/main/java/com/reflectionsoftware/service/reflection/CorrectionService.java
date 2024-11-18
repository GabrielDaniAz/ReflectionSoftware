package com.reflectionsoftware.service.reflection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.reflectionsoftware.model.Template;
import com.reflectionsoftware.model.result.reflection.ReflectionResult;
import com.reflectionsoftware.model.result.reflection.comparison.ComparisonTemplate;
import com.reflectionsoftware.model.result.reflection.comparison.ComparisonClass;

public class CorrectionService {

    public ReflectionResult correct(List<Template> templates, List<Class<?>> studentClasses) {
        List<ComparisonTemplate> comparisonTemplates = templates.stream()
            .map(template -> correctTemplate(template, studentClasses))
            .collect(Collectors.toList());

        return new ReflectionResult(comparisonTemplates);
    }

    private ComparisonTemplate correctTemplate(Template template, List<Class<?>> studentClasses) {
        List<ComparisonClass> comparisonClasses = new ArrayList<>();
        List<String> missingClasses = new ArrayList<>();

        for (Class<?> templateClass : template.getClasses()) {
            Optional<Class<?>> studentClass = ComparisonUtils.getClassByName(templateClass.getSimpleName(), studentClasses);
            if(!studentClass.isPresent()){
                missingClasses.add(templateClass.getSimpleName());
                continue;
            }
            comparisonClasses.add(compareClasses(templateClass, studentClass.get()));
        }

        return new ComparisonTemplate(template.getExerciseName(), comparisonClasses, missingClasses);
    }

    // private ComparisonTemplate correctTemplate(Template template, List<Class<?>> studentClasses) {
    //     List<ComparisonClass> comparisonClasses = template.getClasses().stream()
    //         .map(templateClass -> {
    //             return ComparisonUtils.getClassByName(templateClass.getSimpleName(), studentClasses)
    //                 .map(studentClass -> compareClasses(templateClass, studentClass))
    //                 .orElse(null);
    //         })
    //         .collect(Collectors.toList());

    //     List<String> missingClasses = template.getClasses().stream()
    //         .map(Class::getSimpleName)
    //         .filter(className -> studentClasses.stream()
    //             .noneMatch(studentClass -> studentClass.getSimpleName().equals(className)))
    //         .collect(Collectors.toList());

    //     return new ComparisonTemplate(template.getExerciseName(), comparisonClasses, missingClasses);
    // }

    private ComparisonClass compareClasses(Class<?> templateClass, Class<?> studentClass) {
        return new ComparisonClass(
            studentClass.getSimpleName(),
            FieldService.compareFields(templateClass, studentClass),
            MethodService.compareMethods(templateClass, studentClass),
            ConstructorService.compareConstructors(templateClass, studentClass)
        );
    }
}
