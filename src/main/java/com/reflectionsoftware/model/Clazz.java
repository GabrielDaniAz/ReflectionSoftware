package com.reflectionsoftware.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Clazz {
    
    private final Class<?> clazz;

    public Clazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    /* GENERAL CRITERIA */
    public String getClazzName() {
        return clazz.getSimpleName();
    }

    public boolean areAllFieldsPrivate() {
        return Arrays.stream(clazz.getDeclaredFields())
                     .allMatch(field -> Modifier.isPrivate(field.getModifiers()));
    }

    public boolean hasPublicConstructor() {
        return clazz.getConstructors().length > 0;
    }

    public boolean hasConstructorParameters() {
        return Arrays.stream(clazz.getDeclaredConstructors())
                     .anyMatch(constructor -> constructor.getParameterCount() > 0);
    }

    /* FIELD CRITERIA */
    public boolean[] hasField(String name, String type, boolean needToBePrivate, boolean needToBeFinal) {
        boolean nameMatch = false;
        boolean typeMatch = false;
        boolean privateMatch = true;
        boolean finalMatch = true;

        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(name)) {
                nameMatch = true;
                if (field.getType().getSimpleName().equals(type)) {
                    typeMatch = true;
                }
                if(needToBePrivate) {
                    if(!Modifier.isPrivate(field.getModifiers())) {
                        privateMatch = false;
                    }
                }
                if(needToBeFinal) {
                    if(!Modifier.isFinal(field.getModifiers())) {
                        finalMatch = false;
                    }
                }
                break;
            }
        }
        return new boolean[]{nameMatch, typeMatch, privateMatch, finalMatch};
    }

    /* CONSTRUCTOR CRITERIA */
    public boolean[] hasConstructor(Class<?>[] parameterTypes, String[] parameterNames, String visibility) {
        boolean constructorExist = hasPublicConstructor();
        boolean paramLengthMatch = false;
        boolean paramTypesMatch = false;
        boolean paramNamesMatch = false;
        boolean visibilityMatch = false;

        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (matchParameterLength(constructor, parameterTypes.length)) {
                paramLengthMatch = true;
                paramTypesMatch = matchParameterTypes(constructor, parameterTypes);
                paramNamesMatch = matchParameterNames(constructor, parameterNames);
                visibilityMatch = matchVisibility(constructor, visibility);

                if (paramTypesMatch && paramNamesMatch && visibilityMatch) {
                    break;
                }
            }
        }
        return new boolean[]{constructorExist, paramLengthMatch, paramTypesMatch, paramNamesMatch, visibilityMatch};
    }

    private boolean matchParameterLength(Constructor<?> constructor, int expectedLength) {
        return constructor.getParameterCount() == expectedLength;
    }

    private boolean matchParameterTypes(Constructor<?> constructor, Class<?>[] expectedTypes) {
        return Arrays.equals(constructor.getParameterTypes(), expectedTypes);
    }

    private boolean matchParameterNames(Constructor<?> constructor, String[] expectedNames) {
        Parameter[] constructorParams = constructor.getParameters();
        for (int i = 0; i < expectedNames.length; i++) {
            if (!constructorParams[i].getName().equals(expectedNames[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean matchVisibility(Constructor<?> constructor, String visibility) {
        int modifiers = constructor.getModifiers();
        return switch (visibility.toLowerCase()) {
            case "public" -> Modifier.isPublic(modifiers);
            case "private" -> Modifier.isPrivate(modifiers);
            case "protected" -> Modifier.isProtected(modifiers);
            default -> false;
        };
    }

    /* METHOD CRITERIA */
    public boolean[] hasMethod(String name, Class<?>[] paramTypes, String returnType, String visibility) {
        boolean hasMethodName = false;
        boolean paramLengthMatch = false;
        boolean paramTypesMatch = false;
        boolean returnTypeMatch = false;
        boolean visibilityMatch = false;

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(name)) {
                hasMethodName = true;
                paramLengthMatch = hasMatchingParameterLength(method, paramTypes.length);
                paramTypesMatch = hasMatchingParameterTypes(method, paramTypes);
                returnTypeMatch = hasMatchingReturnType(method, returnType);
                visibilityMatch = hasMatchingVisibility(method, visibility);
            }
        }

        return new boolean[]{hasMethodName, paramLengthMatch, paramTypesMatch, returnTypeMatch, visibilityMatch};
    }

    private boolean hasMatchingParameterLength(Method method, int expectedLength) {
        return method.getParameterCount() == expectedLength;
    }

    private boolean hasMatchingParameterTypes(Method method, Class<?>[] expectedTypes) {
        return Arrays.equals(method.getParameterTypes(), expectedTypes);
    }

    private boolean hasMatchingReturnType(Method method, String expectedReturnType) {
        return method.getReturnType().getSimpleName().equals(expectedReturnType);
    }

    private boolean hasMatchingVisibility(Method method, String expectedVisibility) {
        int modifiers = method.getModifiers();
        return switch (expectedVisibility.toLowerCase()) {
            case "public" -> Modifier.isPublic(modifiers);
            case "private" -> Modifier.isPrivate(modifiers);
            case "protected" -> Modifier.isProtected(modifiers);
            default -> false;
        };
    }

    @Override
    public String toString() {
        String fields = Arrays.stream(clazz.getDeclaredFields())
                              .map(field -> String.format("Field{name='%s', type='%s', visibility='%s'}",
                                      field.getName(), field.getType().getSimpleName(),
                                      Modifier.toString(field.getModifiers())))
                              .collect(Collectors.joining(", "));

        String constructors = Arrays.stream(clazz.getDeclaredConstructors())
                                    .map(constructor -> String.format("Constructor{params=%s, visibility='%s'}",
                                            Arrays.toString(constructor.getParameterTypes()),
                                            Modifier.toString(constructor.getModifiers())))
                                    .collect(Collectors.joining(", "));

        String methods = Arrays.stream(clazz.getDeclaredMethods())
                               .map(method -> String.format("Method{name='%s', returnType='%s', params=%s, visibility='%s'}",
                                       method.getName(), method.getReturnType().getSimpleName(),
                                       Arrays.toString(method.getParameterTypes()),
                                       Modifier.toString(method.getModifiers())))
                               .collect(Collectors.joining(", "));

        return String.format("Clazz{name='%s', fields=[%s], constructors=[%s], methods=[%s]}",
                             getClazzName(), fields, constructors, methods);
    }
}
