package com.reflectionsoftware.service.reflection;

import java.lang.reflect.Constructor;

import com.reflectionsoftware.model.result.correction.exercise.clazz.ClassCorrection;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.ConstructorCorrection;

public class ConstructorService {

    public static void correctConstructors(Class<?> clazz, Class<?> studentClass, ClassCorrection classCorrection) {

        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            Constructor<?> studentConstructor = ComparisonUtils.getMatchingConstructor(constructor, studentClass);
            classCorrection.addConstructorCorrection(correctConstructor(constructor, studentConstructor));
        }

    }

    private static ConstructorCorrection correctConstructor(Constructor<?> constructor, Constructor<?> studentConstructor) {
        ConstructorCorrection constructorCorrection = new ConstructorCorrection(constructor, studentConstructor);

        if(studentConstructor == null){
            return constructorCorrection;
        }

        constructorCorrection.setVisibilityCorrect(ComparisonUtils.checkVisibility(constructor, studentConstructor));
        constructorCorrection.setModifiersCorrect(ComparisonUtils.checkModifiers(constructor, studentConstructor));
        constructorCorrection.setParameterTypesCorrect(ComparisonUtils.checkParameterTypes(constructor, studentConstructor));
        constructorCorrection.setTotalGrade(ComparisonUtils.getTotalGrade(constructor));

        return constructorCorrection;
    }
}
