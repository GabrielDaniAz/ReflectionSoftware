package com.reflectionsoftware.service.reflection;

import java.lang.reflect.Method;

import com.reflectionsoftware.model.result.correction.exercise.clazz.ClassCorrection;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.MethodCorrection;

public class MethodService {

    public static void correctMethods(Class<?> clazz, Class<?> studentClass, ClassCorrection classCorrection) {

        for (Method method : clazz.getDeclaredMethods()) {
            Method studentMethod = ComparisonUtils.getMatchingMethod(method, studentClass);
            classCorrection.addMethodCorrection(correctmethod(method, studentMethod));
        }

    }

    private static MethodCorrection correctmethod(Method method, Method studentMethod) {
        MethodCorrection methodCorrection = new MethodCorrection(method, studentMethod);

        if(studentMethod == null){
            return methodCorrection;
        }

        methodCorrection.setVisibilityCorrect(ComparisonUtils.checkVisibility(method, studentMethod));
        methodCorrection.setModifiersCorrect(ComparisonUtils.checkModifiers(method, studentMethod));
        methodCorrection.setReturnTypeCorrect(ComparisonUtils.checkReturnType(method, studentMethod));
        methodCorrection.setParameterTypesCorrect(ComparisonUtils.checkParameterTypes(method, studentMethod));
        methodCorrection.setTotalGrade(ComparisonUtils.getTotalGrade(method));

        return methodCorrection;
    }
}
