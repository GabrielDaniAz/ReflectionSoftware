package com.reflectionsoftware.service.reflection;

import java.lang.reflect.Field;

import com.reflectionsoftware.model.result.correction.exercise.clazz.ClassCorrection;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.FieldCorrection;

public class FieldService {

    public static void correctFields(Class<?> clazz, Class<?> studentClass, ClassCorrection classCorrection) {
        
        for (Field field : clazz.getDeclaredFields()) {
            Field studentField = ComparisonUtils.getMatchingField(field, studentClass);
            classCorrection.addFieldCorrection(correctField(field, studentField));
        }

    }

    private static FieldCorrection correctField(Field field, Field studentField) {
        FieldCorrection fieldCorrection = new FieldCorrection(field, studentField);

        if(studentField == null){
            return fieldCorrection;
        }

        fieldCorrection.setVisibilityCorrect(ComparisonUtils.checkVisibility(field, studentField));
        fieldCorrection.setModifiersCorrect(ComparisonUtils.checkModifiers(field, studentField));
        fieldCorrection.setTypeCorrect(ComparisonUtils.checkType(field, studentField));
        fieldCorrection.setTotalGrade(ComparisonUtils.getTotalGrade(field));

        return fieldCorrection;
    }
}
