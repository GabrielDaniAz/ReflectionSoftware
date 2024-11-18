package com.reflectionsoftware.model.result.reflection.comparison;

import java.util.List;

import com.reflectionsoftware.model.result.reflection.comparison.specification.ComparisonConstructor;
import com.reflectionsoftware.model.result.reflection.comparison.specification.ComparisonField;
import com.reflectionsoftware.model.result.reflection.comparison.specification.ComparisonMethod;

public class ComparisonClass {
    private final String className;
    private final List<ComparisonField> fields;
    private final List<ComparisonMethod> methods;
    private final List<ComparisonConstructor> constructors;

    public ComparisonClass(String className, List<ComparisonField> fields, List<ComparisonMethod> methods, List<ComparisonConstructor> constructors) {
        this.className = className;
        this.fields = fields;
        this.methods = methods;
        this.constructors = constructors;
    }

    public String getClassName() { return className; }
    public List<ComparisonField> getComparisionFields() { return fields; }
    public List<ComparisonMethod> getComparisionMethods() { return methods; }
    public List<ComparisonConstructor> getComparisionConstructors() { return constructors; }
}
