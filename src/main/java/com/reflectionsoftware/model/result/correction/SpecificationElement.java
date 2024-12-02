package com.reflectionsoftware.model.result.correction;

import com.reflectionsoftware.util.reflection.ElementUtils;

public abstract class SpecificationElement<T> implements ISpecificationElement{
    
    protected final T template; // Field, Method ou Constructor do Template
    protected final T student;  // Field, Method ou Constructor do Estudante

    public SpecificationElement(T template, T student) {
        this.template = template;
        this.student = student;
    }

    @Override
    public boolean hasTemplate() { return template != null; }

    @Override
    public boolean hasStudent() { return student != null; }

    @Override
    public boolean hasCorrection() { return hasTemplate() && hasStudent(); }

    @Override
    public boolean checkVisibility() { return ElementUtils.checkVisibility(template, student); }

    @Override
    public boolean checkModifiers() { return ElementUtils.checkModifiers(template, student); }

    @Override
    public boolean checkReturnType() { return ElementUtils.checkReturnType(template, student); };
    
    @Override
    public boolean checkType() { return ElementUtils.checkType(template, student); };

    @Override
    public boolean checkParameters() { return ElementUtils.checkParameters(template, student); };

    @Override
    public double getGrade() { return ElementUtils.getGrade(template); }

    @Override 
    public abstract double getObtainedGrade();   

    @Override
    public abstract String templateString();

    @Override
    public abstract String studentString();

    @Override
    public String toString() {
        if(hasTemplate()) return templateString();
        if(hasStudent()) return studentString();
        return "";
    }
}
