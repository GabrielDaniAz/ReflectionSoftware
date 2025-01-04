package com.reflectionsoftware.model.result.correction.exercise.clazz.specification;

import java.lang.reflect.Method;

import com.reflectionsoftware.model.result.correction.SpecificationElement;

public class MethodCorrection extends SpecificationElement<Method>{

    // public static double methodName(int ano);
    // visibility modifier return name parameters
    public MethodCorrection(Method template, Method student) {
        super(template, student);
    }

    public double getObtainedGrade() {
        double score = 0;

        if (checkVisibility()) score += 0.2;
        if (checkModifiers()) score += 0.2;
        if (checkReturnType()) score += 0.2;
        if (checkParameters()) score += 0.2;
        if (checkTest()) score += 0.2;

        return score * getGrade();
    }

    @Override
    public String templateString() {
        if(!super.hasTemplate()) {
            return "";
        }

        return template.getName();
    }

    @Override
    public String studentString() {
        if(!super.hasStudent()) {
            return "";
        }

        return student.getName();
    }
}
