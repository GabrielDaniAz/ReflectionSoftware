package com.reflectionsoftware.service.reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.reflectionsoftware.model.result.reflection.comparison.specification.ComparisonMethod;

public class MethodService {

    private static final String[] MODIFIER_KEYS = { "static", "final", "abstract", "synchronized" };

    public static List<ComparisonMethod> compareMethods(Class<?> templateClass, Class<?> studentClass) {
        List<ComparisonMethod> methodComparisons = new ArrayList<>();

        for (Method templateMethod : templateClass.getDeclaredMethods()) {
            Optional<Method> studentMethodOpt = ComparisonUtils.findMethod(studentClass, templateMethod.getName(), templateMethod.getParameterTypes());

            ComparisonMethod comparison = studentMethodOpt
                .map(studentMethod -> buildComparison(templateMethod, studentMethod))
                .orElseGet(() -> buildAbsentComparison(templateMethod));

            methodComparisons.add(comparison);
        }

        return methodComparisons;
    }

    private static ComparisonMethod buildComparison(Method templateMethod, Method studentMethod) {
        String methodName = templateMethod.getName();

        boolean isVisibilityCorrect = ComparisonUtils.isVisibilityMatching(templateMethod.getModifiers(), studentMethod.getModifiers());
        boolean isModifierCorrect = ComparisonUtils.areModifiersMatching(templateMethod.getModifiers(), studentMethod.getModifiers(), MODIFIER_KEYS);
        boolean isReturnTypeCorrect = templateMethod.getReturnType().getSimpleName().equals(studentMethod.getReturnType().getSimpleName());
        boolean isParamTypesCorrect = ComparisonUtils.areParameterTypesMatching(templateMethod.getParameterTypes(), studentMethod.getParameterTypes());

        return new ComparisonMethod(
            methodName,
            true,
            isVisibilityCorrect,
            isModifierCorrect,
            isReturnTypeCorrect,
            isParamTypesCorrect,
            ComparisonUtils.getVisibility(templateMethod.getModifiers()),
            ComparisonUtils.getVisibility(studentMethod.getModifiers()),
            ComparisonUtils.getModifiers(templateMethod.getModifiers(), MODIFIER_KEYS),
            ComparisonUtils.getModifiers(studentMethod.getModifiers(), MODIFIER_KEYS),
            templateMethod.getReturnType().getSimpleName(),
            studentMethod.getReturnType().getSimpleName(),
            ComparisonUtils.formatParameterTypes(templateMethod.getParameterTypes()),
            ComparisonUtils.formatParameterTypes(studentMethod.getParameterTypes()),
            ComparisonUtils.calculateGrade(isVisibilityCorrect, isModifierCorrect, isReturnTypeCorrect, isParamTypesCorrect)
        );
    }

    private static ComparisonMethod buildAbsentComparison(Method templateMethod) {
        return new ComparisonMethod(
            templateMethod.getName(),
            false,
            false,
            false,
            false,
            false,
            ComparisonUtils.getVisibility(templateMethod.getModifiers()),
            "ausente",
            ComparisonUtils.getModifiers(templateMethod.getModifiers(), MODIFIER_KEYS),
            "ausente",
            templateMethod.getReturnType().getSimpleName(),
            "ausente",
            ComparisonUtils.formatParameterTypes(templateMethod.getParameterTypes()),
            "ausente",
            0
        );
    }
}
