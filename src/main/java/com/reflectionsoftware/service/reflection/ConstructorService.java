package com.reflectionsoftware.service.reflection;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.reflectionsoftware.model.result.reflection.comparison.specification.ComparisonConstructor;

public class ConstructorService {

    private static final String[] MODIFIER_KEYS = { "static", "final", "synchronized" };

    public static List<ComparisonConstructor> compareConstructors(Class<?> templateClass, Class<?> studentClass) {
        List<ComparisonConstructor> constructorComparisons = new ArrayList<>();

        for (Constructor<?> templateConstructor : templateClass.getDeclaredConstructors()) {
            Optional<Constructor<?>> matchingConstructor = ComparisonUtils.findConstructor(studentClass, templateConstructor.getParameterTypes());

            constructorComparisons.add(
                matchingConstructor
                    .map(constructor -> buildComparison(templateConstructor, constructor))
                    .orElseGet(() -> buildAbsentComparison(templateConstructor))
            );
        }

        return constructorComparisons;
    }

    private static ComparisonConstructor buildComparison(Constructor<?> templateConstructor, Constructor<?> studentConstructor) {
        boolean isVisibilityCorrect = ComparisonUtils.isVisibilityMatching(templateConstructor.getModifiers(), studentConstructor.getModifiers());
        boolean isModifierCorrect = ComparisonUtils.areModifiersMatching(templateConstructor.getModifiers(), studentConstructor.getModifiers(), MODIFIER_KEYS);
        boolean isParamTypesCorrect = ComparisonUtils.areParameterTypesMatching(templateConstructor.getParameterTypes(), studentConstructor.getParameterTypes());

        return createComparisonConstructor(
            templateConstructor.getName(),
            true,
            isVisibilityCorrect,
            isModifierCorrect,
            isParamTypesCorrect,
            templateConstructor,
            studentConstructor,
            ComparisonUtils.calculateGrade(isVisibilityCorrect, isModifierCorrect, isParamTypesCorrect)
        );
    }

    private static ComparisonConstructor buildAbsentComparison(Constructor<?> templateConstructor) {
        return createComparisonConstructor(
            templateConstructor.getName(),
            false,
            false,
            false,
            false,
            templateConstructor,
            null,
            0
        );
    }

    private static ComparisonConstructor createComparisonConstructor(
            String constructorName, boolean exists, boolean isVisibilityCorrect, boolean isModifierCorrect,
            boolean isParamTypesCorrect, Constructor<?> templateConstructor, Constructor<?> studentConstructor, int grade) {

        return new ComparisonConstructor(
            constructorName,
            exists,
            isVisibilityCorrect,
            isModifierCorrect,
            isParamTypesCorrect,
            ComparisonUtils.getVisibility(templateConstructor.getModifiers()),
            studentConstructor != null ? ComparisonUtils.getVisibility(studentConstructor.getModifiers()) : "ausente",
            ComparisonUtils.getModifiers(templateConstructor.getModifiers(), MODIFIER_KEYS),
            studentConstructor != null ? ComparisonUtils.getModifiers(studentConstructor.getModifiers(), MODIFIER_KEYS) : "ausente",
            ComparisonUtils.formatParameterTypes(templateConstructor.getParameterTypes()),
            studentConstructor != null ? ComparisonUtils.formatParameterTypes(studentConstructor.getParameterTypes()) : "ausente",
            grade
        );
    }
}
