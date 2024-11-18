package com.reflectionsoftware.service.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ComparisonUtils {

    public static Optional<Field> getFieldByName(String fieldName, Field[] fields) {
        return Arrays.stream(fields).filter(field -> field.getName().equals(fieldName)).findFirst();
    }

    public static Optional<Class<?>> getClassByName(String className, List<Class<?>> classes) {
        return classes.stream().filter(clazz -> clazz.getSimpleName().equals(className)).findFirst();
    }

    public static String getVisibility(int modifiers) {
        if (Modifier.isPrivate(modifiers)) return "private";
        if (Modifier.isProtected(modifiers)) return "protected";
        if (Modifier.isPublic(modifiers)) return "public";
        return "default";
    }

    public static String getModifiers(int modifiers, String... allowedModifiers) {
        return Arrays.stream(allowedModifiers)
                     .filter(modifier -> Modifier.toString(modifiers).contains(modifier))
                     .collect(Collectors.joining(" "));
    }

    public static Optional<Constructor<?>> findConstructor(Class<?> clazz, Class<?>[] paramTypes) {
        try {
            return Optional.of(clazz.getDeclaredConstructor(paramTypes));
        } catch (NoSuchMethodException e) {
            Optional<Constructor<?>> constructor = Arrays.stream(clazz.getDeclaredConstructors())
                         .filter(c -> areParameterTypesMatching(paramTypes, c.getParameterTypes()))
                         .findFirst();
            if(constructor.isPresent()) {
                return constructor;
            }

            constructor = Arrays.stream(clazz.getDeclaredConstructors())
                        .filter(c -> c.getParameterCount() == paramTypes.length)
                        .findFirst();

            if(constructor.isPresent()){
                return constructor;
            }

            constructor = Arrays.stream(clazz.getDeclaredConstructors()).findFirst();
            return constructor;
        }
    }

    public static Optional<Method> findMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes) {
        // Passo 1: Encontrar o método com nome e parâmetros iguais (mesma ordem)
        try {
            return Optional.of(clazz.getDeclaredMethod(methodName, paramTypes));  // Exato: mesmo nome e tipos de parâmetros na mesma ordem
        } catch (NoSuchMethodException e) {
            // Passo 2: Procurar métodos com nome e parâmetros iguais (ordem diferente)
            Optional<Method> method = Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getName().equals(methodName) && areParameterTypesMatching(paramTypes, m.getParameterTypes()))
                .findFirst();
            if (method.isPresent()) {
                return method;  // Encontrado método com parâmetros na mesma ordem, mas ordem diferente.
            }
    
            // Passo 3: Procurar métodos com nome e mesma quantidade de parâmetros (parâmetros podem ser diferentes)
            method = Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getName().equals(methodName) && m.getParameterCount() == paramTypes.length)
                .findFirst();
            if (method.isPresent()) {
                return method;  // Encontrado método com o mesmo nome e número de parâmetros.
            }
    
            // Passo 4: Procurar métodos com nome igual apenas
            return Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getName().equals(methodName))
                .findFirst();  // Encontrar método com nome igual, independentemente dos parâmetros.
        }
    }

    public static String formatParameterTypes(Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes).map(Class::getSimpleName).collect(Collectors.joining(", "));
    }

    public static int calculateGrade(boolean... conditions) {
        int correctCount = 0;
        for (boolean condition : conditions) {
            if (condition) {
                correctCount++;
            }
        }
        return Math.round((correctCount / (float) conditions.length) * 100);
    }

    public static boolean areParameterTypesMatching(Class<?>[] paramTypes1, Class<?>[] paramTypes2) {
        return Arrays.equals(paramTypes1, paramTypes2);
    }

    public static boolean isVisibilityMatching(int modifier1, int modifier2) {
        return getVisibility(modifier1).equals(getVisibility(modifier2));
    }

    public static boolean areModifiersMatching(int modifier1, int modifier2, String... allowedModifiers) {
        return getModifiers(modifier1, allowedModifiers).equals(getModifiers(modifier2, allowedModifiers));
    }
}
