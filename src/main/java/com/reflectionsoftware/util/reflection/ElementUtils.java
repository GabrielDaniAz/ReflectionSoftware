package com.reflectionsoftware.util.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;

public class ElementUtils {

    public static boolean checkVisibility(Object template, Object student) {
        if (!canValidateVisibility(template, student)) return false;

        int templateModifiers = getModifiers(template);
        int studentModifiers = getModifiers(student);

        return (Modifier.isPublic(templateModifiers) == Modifier.isPublic(studentModifiers)) &&
               (Modifier.isProtected(templateModifiers) == Modifier.isProtected(studentModifiers)) &&
               (Modifier.isPrivate(templateModifiers) == Modifier.isPrivate(studentModifiers));
    }

    public static boolean checkModifiers(Object template, Object student) {
        if (!canValidateModifiers(template, student)) return false;

        int templateModifiers = getModifiers(template);
        int studentModifiers = getModifiers(student);

        boolean areStatic = Modifier.isStatic(templateModifiers) == Modifier.isStatic(studentModifiers);
        boolean areFinal = Modifier.isFinal(templateModifiers) == Modifier.isFinal(studentModifiers);

        return areStatic && areFinal;
    }

    public static boolean checkParameters(Object template, Object student) {
        if (!canValidateParameters(template, student)) return false;

        Class<?>[] templateParams = getParameterTypes(template);
        Class<?>[] studentParams = getParameterTypes(student);

        return Arrays.equals(templateParams, studentParams);
    }

    public static boolean checkReturnType(Object template, Object student) {
        if (!canValidateReturnType(template, student)) return false;

        Method templateMethod = (Method) template;
        Method studentMethod = (Method) student;

        return templateMethod.getReturnType().getSimpleName().equals(studentMethod.getReturnType().getSimpleName());
    }

    public static boolean checkType(Object template, Object student) {
        if (!canValidateType(template, student)) return false;

        Field templateField = (Field) template;
        Field studentField = (Field) student;

        return templateField.getType().equals(studentField.getType());
    }

    public static double getGrade(Object element) {
        if(element == null) return 0.0;
        
        if (!(element instanceof AnnotatedElement)) return 0.0;

        AnnotatedElement annotatedElement = (AnnotatedElement) element;

        for (Annotation annotation : annotatedElement.getAnnotations()) {
            if (annotation.annotationType().getSimpleName().equals("Nota")) {
                try {
                    return (Double) annotation.annotationType().getMethod("valor").invoke(annotation);
                } catch (Exception e) {
                    return 0.0;
                }
            }
        }

        return 0.0;
    }

    // --- Métodos Auxiliares ---

    private static int getModifiers(Object element) {
        if (element instanceof Field) return ((Field) element).getModifiers();
        if (element instanceof Method) return ((Method) element).getModifiers();
        if (element instanceof Constructor) return ((Constructor<?>) element).getModifiers();
        return 0;
    }

    private static Class<?>[] getParameterTypes(Object element) {
        if (element instanceof Method) return ((Method) element).getParameterTypes();
        if (element instanceof Constructor) return ((Constructor<?>) element).getParameterTypes();
        return new Class<?>[0];
    }


    // --- VALIDADORES ---

    private static boolean canValidateVisibility(Object element1, Object element2) {
        if (element1 == null || element2 == null) {
            return false;
        }

        return (element1 instanceof Constructor<?> && element2 instanceof Constructor<?>) ||
               (element1 instanceof Field && element2 instanceof Field) ||
               (element1 instanceof Method && element2 instanceof Method);
    }

    private static boolean canValidateModifiers(Object element1, Object element2) {
        if (element1 == null || element2 == null) {
            return false;
        }

        return  (element1 instanceof Constructor<?> && element2 instanceof Constructor<?>) ||
                (element1 instanceof Field && element2 instanceof Field) ||
                (element1 instanceof Method && element2 instanceof Method);
    }

    private static boolean canValidateReturnType(Object element1, Object element2) {
        if (element1 == null || element2 == null) {
            return false;
        }

        return (element1 instanceof Method && element2 instanceof Method);
    }  

    private static boolean canValidateParameters(Object element1, Object element2) {
        if (element1 == null || element2 == null) {
            return false;
        }

        return (element1 instanceof Method && element2 instanceof Method) ||
               (element1 instanceof Constructor<?> && element2 instanceof Constructor<?>);
    }

    private static boolean canValidateType(Object element1, Object element2) {
        if (element1 == null || element2 == null) {
            return false;
        }

        return (element1 instanceof Field && element2 instanceof Field);
    }
    
}
