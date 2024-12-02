package com.reflectionsoftware.model.result.correction.exercise.clazz.specification;

import java.lang.reflect.Constructor;
import com.reflectionsoftware.model.result.correction.SpecificationElement;

public class ConstructorCorrection extends SpecificationElement<Constructor<?>> {

    // public abstract name (int ano)
    // visibility modifiers name parameters
    public ConstructorCorrection(Constructor<?> template, Constructor<?> student) {
        super(template, student);
    }

    public double getObtainedGrade() {
        double score = 0;
        if (checkVisibility()) score += 0.3;
        if (checkModifiers()) score += 0.3;
        if (checkParameters()) score += 0.4;
        return score * getGrade();
    }

    @Override
    public String templateString() {
        if(!super.hasTemplate()) {
            return "";
        }

        return convertParameterTypesToString(template);
    }

    @Override
    public String studentString() {
        if(!super.hasStudent()) {
            return "";
        }

        return convertParameterTypesToString(student);
    }

    private String convertParameterTypesToString(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        StringBuilder sb = new StringBuilder("(");

        for (Class<?> paramType : parameterTypes) {
            sb.append(paramType.getSimpleName()).append(", ");
        }

        // Remove a última vírgula e espaço
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }

        sb.append(")");

        return sb.toString();
    }
}
