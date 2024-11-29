package com.reflectionsoftware.model.result.correction.exercise.clazz.specification;

import java.lang.reflect.Field;

import com.reflectionsoftware.model.result.correction.SpecificationElement;

public class FieldCorrection extends SpecificationElement<Field>{

    // private static int ano;
    // visibility modifier type name
    public FieldCorrection(Field template, Field student) {
        super(template, student);
    }

    public double getObtainedGrade() {
        double score = 0;

        if (checkVisibility()) score += 0.3;
        if (checkModifiers()) score += 0.3;
        if (checkType()) score += 0.4;

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

    @Override
    public String toString() {
        return "Implementar";
    }
}
