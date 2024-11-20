package com.reflectionsoftware.service.reflection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.reflectionsoftware.model.clazz.ClassInfo;
import com.reflectionsoftware.model.clazz.specification.ConstructorInfo;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.ConstructorCorrection;

public class ConstructorService {

    public static List<ConstructorCorrection> compareConstructors(ClassInfo templateClass, ClassInfo studentClass) {
        List<ConstructorCorrection> constructorCorrections = new ArrayList<>();

        for (ConstructorInfo templateConstructor : templateClass.getConstructors()) {
            constructorCorrections.add(compareSingleConstructor(templateConstructor, studentClass));
        }

        return constructorCorrections;
    }

    private static ConstructorCorrection compareSingleConstructor(ConstructorInfo templateConstructor, ClassInfo studentClass) {
        Optional<ConstructorInfo> constructorOpt = ComparisonUtils.findConstructor(studentClass, templateConstructor.getParameterTypes());

        if(constructorOpt.isPresent()) {
            ConstructorInfo studentConstructor = constructorOpt.get();
            return new ConstructorCorrection(
                templateConstructor,
                studentConstructor,
                studentConstructor.getVisibility().equals(templateConstructor.getVisibility()), 
                studentConstructor.getModifiers().equals(templateConstructor.getModifiers()), 
                studentConstructor.getName().equals(templateConstructor.getName()), 
                studentConstructor.getParameterTypes().equals(templateConstructor.getParameterTypes()));
        }

        return new ConstructorCorrection(templateConstructor);
    }
}
