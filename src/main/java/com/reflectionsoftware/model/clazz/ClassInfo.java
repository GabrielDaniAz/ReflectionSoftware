package com.reflectionsoftware.model.clazz;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.reflectionsoftware.model.clazz.specification.ConstructorInfo;
import com.reflectionsoftware.model.clazz.specification.FieldInfo;
import com.reflectionsoftware.model.clazz.specification.MethodInfo;
import com.reflectionsoftware.model.result.compilation.CompilationResult;

public class ClassInfo {
    private final CompilationResult compilationResult;

    private final String className;

    private final List<FieldInfo> fields;
    private final List<MethodInfo> methods;
    private final List<ConstructorInfo> constructors;

    public ClassInfo(Class<?> clazz) {
        this.compilationResult = null;
        this.className = clazz.getName();
        this.fields = extractFields(clazz);
        this.methods = extractMethods(clazz);
        this.constructors = extractConstructors(clazz);
    }

    public ClassInfo(CompilationResult compilationResult) {
        this.compilationResult = compilationResult;
        this.className = null;
        this.fields = null;
        this.methods = null;
        this.constructors = null;
    }

    public CompilationResult getCompilationResult() { return compilationResult; }

    public String getClassName() { return className; }

    public List<FieldInfo> getFields() { return fields; }
    public List<MethodInfo> getMethods() { return methods; }
    public List<ConstructorInfo> getConstructors() { return constructors; }

    private List<FieldInfo> extractFields(Class<?> clazz) {
        List<FieldInfo> fieldInfos = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            fieldInfos.add(new FieldInfo(field));
        }
        return fieldInfos;
    }

    private List<MethodInfo> extractMethods(Class<?> clazz) {
        List<MethodInfo> methodInfos = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            methodInfos.add(new MethodInfo(method));
        }
        return methodInfos;
    }

    private List<ConstructorInfo> extractConstructors(Class<?> clazz) {
        List<ConstructorInfo> constructorInfos = new ArrayList<>();
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            constructorInfos.add(new ConstructorInfo(constructor));
        }
        return constructorInfos;
    }

    @Override
    public String toString() {
        return className + "{\n" +
                fields + "\n" + 
                constructors + "\n" + 
                methods + "\n" + 
                '}';
    }
}
