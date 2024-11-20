package com.reflectionsoftware.service.reflection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.reflectionsoftware.model.clazz.ClassInfo;
import com.reflectionsoftware.model.clazz.specification.MethodInfo;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.MethodCorrection;

public class MethodService {

    public static List<MethodCorrection> compareMethods(ClassInfo templateClass, ClassInfo studentClass) {
        List<MethodCorrection> methodCorrections = new ArrayList<>();

        for (MethodInfo templateMethod : templateClass.getMethods()) {
            methodCorrections.add(compareSingleMethod(templateMethod, studentClass));
        }

        return methodCorrections;
    }

    private static MethodCorrection compareSingleMethod(MethodInfo templateMethod, ClassInfo studentClass) {
        Optional<MethodInfo> methodOpt = ComparisonUtils.findMethod(studentClass, templateMethod.getName(), templateMethod.getParameterTypes());

        if(methodOpt.isPresent()) {
            MethodInfo studentMethod = methodOpt.get();
            return new MethodCorrection(
                templateMethod,
                studentMethod,
                templateMethod.getName(),
                studentMethod.getVisibility().equals(templateMethod.getVisibility()), 
                studentMethod.getModifiers().equals(templateMethod.getModifiers()), 
                studentMethod.getReturnType().equals(templateMethod.getReturnType()), 
                studentMethod.getName().equals(templateMethod.getName()), 
                studentMethod.getParameterTypes().equals(templateMethod.getParameterTypes()));
        }

        return new MethodCorrection(templateMethod);
    }
}
