package com.reflectionsoftware.service.reflection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.reflectionsoftware.model.clazz.ClassInfo;
import com.reflectionsoftware.model.clazz.specification.FieldInfo;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.FieldCorrection;

public class FieldService {

    public static List<FieldCorrection> compareFields(ClassInfo templateClass, ClassInfo clazz) {
        List<FieldCorrection> fieldCorrections = new ArrayList<>();

        for (FieldInfo templateField : templateClass.getFields()) {
            fieldCorrections.add(compareSingleField(templateField, clazz));
        }

        return fieldCorrections;
    }

    private static FieldCorrection compareSingleField(FieldInfo templateField, ClassInfo clazz) {

        Optional<FieldInfo> fieldOpt = ComparisonUtils.getFieldByName(templateField.getName(), clazz.getFields());

        if(fieldOpt.isPresent()){
            FieldInfo studentField = fieldOpt.get();

            return new FieldCorrection(
                templateField,
                studentField, 
                studentField.getName(), 
                studentField.getVisibility().equals(templateField.getVisibility()), 
                studentField.getModifiers().equals(templateField.getModifiers()), 
                studentField.getType().equals(templateField.getType()),
                studentField.getName().equals(templateField.getName()));
        }

        return new FieldCorrection(templateField);
    }
}
