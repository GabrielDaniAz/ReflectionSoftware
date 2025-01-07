package com.javacorrige.model.result.correction;

public interface ISpecificationElement {
    public boolean hasTemplate();
    public boolean hasStudent();
    public boolean hasCorrection();

    public boolean checkVisibility();
    public boolean checkModifiers();
    public boolean checkReturnType();
    public boolean checkType();
    public boolean checkParameters();

    public double getGrade();
    public double getObtainedGrade();

    public String templateString();
    public String studentString();

    @Override
    public String toString();
}
