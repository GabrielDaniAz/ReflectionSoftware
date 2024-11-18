package com.reflectionsoftware.service.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.reflectionsoftware.model.result.reflection.comparison.specification.ComparisonField;

public class FieldService {

    private static final String[] MODIFIER_KEYS = { "static", "final", "volatile", "transient" };  

    public static List<ComparisonField> compareFields(Class<?> templateClass, Class<?> clazz) {
        List<ComparisonField> fieldComparisons = new ArrayList<>();

        for (Field templateField : templateClass.getDeclaredFields()) {
            fieldComparisons.add(compareSingleField(templateField, clazz));
        }

        return fieldComparisons;
    }

    private static ComparisonField compareSingleField(Field templateField, Class<?> clazz) {
        String fieldName = templateField.getName();
        String expectedType = templateField.getType().getSimpleName();
        String expectedVisibility = ComparisonUtils.getVisibility(templateField.getModifiers());
        String expectedModifiers = ComparisonUtils.getModifiers(templateField.getModifiers(), MODIFIER_KEYS);

        Optional<Field> fieldOpt = ComparisonUtils.getFieldByName(fieldName, clazz.getDeclaredFields());
        
        if (fieldOpt.isPresent()) {
            Field field = fieldOpt.get();
            return buildComparisonField(templateField, field, expectedVisibility, expectedModifiers, expectedType);
        } else {
            return buildMissingField(fieldName, expectedVisibility, expectedModifiers, expectedType);
        }
    }

    private static ComparisonField buildComparisonField(Field templateField, Field field,
                                                        String expectedVisibility, String expectedModifiers, String expectedType) {
        String actualType = field.getType().getSimpleName();
        String actualVisibility = ComparisonUtils.getVisibility(field.getModifiers());
        String actualModifiers = ComparisonUtils.getModifiers(field.getModifiers(), MODIFIER_KEYS);

        boolean isVisibilityCorrect = expectedVisibility.equals(actualVisibility);
        boolean isModifierCorrect = expectedModifiers.equals(actualModifiers);
        boolean isTypeCorrect = expectedType.equals(actualType);

        return new ComparisonField(
            templateField.getName(),
            true,
            isVisibilityCorrect,
            isModifierCorrect,
            isTypeCorrect,
            expectedVisibility,
            actualVisibility,
            expectedModifiers,
            actualModifiers,
            expectedType,
            actualType,
            ComparisonUtils.calculateGrade(isVisibilityCorrect, isModifierCorrect, isTypeCorrect)
        );
    }

    private static ComparisonField buildMissingField(String fieldName, String expectedVisibility, 
                                                     String expectedModifiers, String expectedType) {
        return new ComparisonField(
            fieldName,
            false,
            false,
            false,
            false,
            expectedVisibility,
            "ausente",
            expectedModifiers,
            "ausente",
            expectedType,
            "ausente",
            0
        );
    }
}
