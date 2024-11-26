package com.reflectionsoftware.service.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class ComparisonUtils {

    // -- CLASS --

    public static Class<?> getClassByName(Class<?> clazz, List<Class<?>> classes) {
        return classes.stream().filter(c -> c.getSimpleName().equals(clazz.getSimpleName())).findFirst().orElse(null);
    }



    // -- CONSTRUCTORS --

    public static Constructor<?> getMatchingConstructor(Constructor<?> constructor, Class<?> studentClass) {
        return Arrays.stream(studentClass.getDeclaredConstructors())
                     .filter(c -> c.getName().equals(constructor.getName()))
                     .findFirst()
                     .orElse(null);
    }

    public static boolean checkVisibility(Constructor<?> constructor, Constructor<?> studentConstructor) {
        int modifiers = constructor.getModifiers();
        int studentModifiers = studentConstructor.getModifiers();

        return (Modifier.isPublic(modifiers) == Modifier.isPublic(studentModifiers)) &&
               (Modifier.isProtected(modifiers) == Modifier.isProtected(studentModifiers)) &&
               (Modifier.isPrivate(modifiers) == Modifier.isPrivate(studentModifiers));
    }

    public static boolean checkModifiers(Constructor<?> constructor, Constructor<?> studentConstructor) {
        int modifiers = constructor.getModifiers();
        int studentModifiers = studentConstructor.getModifiers();

        return (Modifier.isStatic(modifiers) == Modifier.isStatic(studentModifiers)) &&
               (Modifier.isFinal(modifiers) == Modifier.isFinal(studentModifiers));
    }

    public static boolean checkParameterTypes(Constructor<?> constructor, Constructor<?> studentConstructor) {
        Class<?>[] templateParamTypes = constructor.getParameterTypes();
        Class<?>[] studentParamTypes = studentConstructor.getParameterTypes();
    
        if (templateParamTypes.length != studentParamTypes.length) {
            return false;
        }

        for (int i = 0; i < templateParamTypes.length; i++) {
            if (!templateParamTypes[i].getName().equals(studentParamTypes[i].getName())) {
                return false;
            }
        }
    
        return true;
    }

    public static double getTotalGrade(Constructor<?> constructor) {
        Annotation[] annotations = constructor.getAnnotations();
        for (Annotation a : annotations) {
            if (a.annotationType().getName().equals("com.gabrieldani.Nota")) {
                try {
                    double valor = (Double) a.getClass().getMethod("valor").invoke(a);
                    return valor;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0.0;
                }
            }
        }
        return 0.0;
    }



    // -- FIELDS --

    public static Field getMatchingField(Field field, Class<?> studentClass) {
        return Arrays.stream(studentClass.getDeclaredFields())
                     .filter(f -> f.getName().equals(field.getName()))
                     .findFirst()
                     .orElse(null);
    }

    public static boolean checkVisibility(Field field, Field studentField) {
        int modifiers = field.getModifiers();
        int studentModifiers = studentField.getModifiers();

        return (Modifier.isPublic(modifiers) == Modifier.isPublic(studentModifiers)) &&
               (Modifier.isProtected(modifiers) == Modifier.isProtected(studentModifiers)) &&
               (Modifier.isPrivate(modifiers) == Modifier.isPrivate(studentModifiers));
    }

    public static boolean checkModifiers(Field field, Field studentField) {
        int modifiers = field.getModifiers();
        int studentModifiers = studentField.getModifiers();

        return (Modifier.isStatic(modifiers) == Modifier.isStatic(studentModifiers)) &&
               (Modifier.isFinal(modifiers) == Modifier.isFinal(studentModifiers));
    }

    public static boolean checkType(Field field, Field studentField) {
        return field.getType().getSimpleName().equals(studentField.getType().getSimpleName());
    }

    public static double getTotalGrade(Field field) {
        Annotation[] annotations = field.getAnnotations();
        for (Annotation a : annotations) {
            if (a.annotationType().getName().equals("com.gabrieldani.Nota")) {
                try {
                    double valor = (Double) a.getClass().getMethod("valor").invoke(a);
                    return valor;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0.0;
                }
            }
        }
        return 0.0;
    }
    

    // -- METHODS --

    public static Method getMatchingMethod(Method method, Class<?> studentClass) {
        return Arrays.stream(studentClass.getDeclaredMethods())
                     .filter(m -> m.getName().equals(method.getName()))
                     .findFirst()
                     .orElse(null);
    }

    public static boolean checkVisibility(Method Method, Method studentMethod) {
        int modifiers = Method.getModifiers();
        int studentModifiers = studentMethod.getModifiers();

        return (Modifier.isPublic(modifiers) == Modifier.isPublic(studentModifiers)) &&
               (Modifier.isProtected(modifiers) == Modifier.isProtected(studentModifiers)) &&
               (Modifier.isPrivate(modifiers) == Modifier.isPrivate(studentModifiers));
    }

    public static boolean checkModifiers(Method Method, Method studentMethod) {
        int modifiers = Method.getModifiers();
        int studentModifiers = studentMethod.getModifiers();

        return (Modifier.isStatic(modifiers) == Modifier.isStatic(studentModifiers)) &&
               (Modifier.isFinal(modifiers) == Modifier.isFinal(studentModifiers));
    }

    public static boolean checkReturnType(Method method, Method studentMethod) {
        return method.getReturnType().getSimpleName().equals(studentMethod.getReturnType().getSimpleName());
    }

    public static boolean checkParameterTypes(Method method, Method studentMethod) {
        Class<?>[] templateParamTypes = method.getParameterTypes();
        Class<?>[] studentParamTypes = studentMethod.getParameterTypes();
    
        if (templateParamTypes.length != studentParamTypes.length) {
            return false;
        }

        for (int i = 0; i < templateParamTypes.length; i++) {
            if (!templateParamTypes[i].getName().equals(studentParamTypes[i].getName())) {
                return false;
            }
        }
    
        return true;
    }

    public static double getTotalGrade(Method method) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation a : annotations) {
            if (a.annotationType().getName().equals("com.gabrieldani.Nota")) {
                try {
                    double valor = (Double) a.getClass().getMethod("valor").invoke(a);
                    return valor;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0.0;
                }
            }
        }
        return 0.0;
    }
}


    // public static Optional<ConstructorInfo> findConstructor(ClassInfo clazz, String paramTypes) {
    //     List<ConstructorInfo> constructors = clazz.getConstructors();

    //     // Passo 1: Encontrar construtor com parâmetros exatos (mesma ordem e tipos)
    //     Optional<ConstructorInfo> exactMatch = constructors.stream()
    //         .filter(c -> c.getParameterTypes().equals(paramTypes))
    //         .findFirst();
    //     if (exactMatch.isPresent()) {
    //         return exactMatch;
    //     }

    //     // Passo 2: Encontrar construtor com os mesmos parâmetros, mas em ordem diferente
    //     Optional<ConstructorInfo> unorderedMatch = constructors.stream()
    //         .filter(c -> areParameterTypesMatchingUnordered(paramTypes, c.getParameterTypes()))
    //         .findFirst();
    //     if (unorderedMatch.isPresent()) {
    //         return unorderedMatch;
    //     }

    //     // Passo 3: Encontrar construtor com a mesma quantidade de parâmetros
    //     Optional<ConstructorInfo> paramCountMatch = constructors.stream()
    //         .filter(c -> countParameters(paramTypes) == countParameters(c.getParameterTypes()))
    //         .findFirst();
    //     if (paramCountMatch.isPresent()) {
    //         return paramCountMatch;
    //     }

    //     // Passo 4: Retornar qualquer construtor (apenas para casos em que nenhum critério acima é atendido)
    //     return constructors.stream().findFirst();
    // }


    // public static Optional<MethodInfo> findMethod(ClassInfo clazz, String methodName, String paramTypes) {
    //     List<MethodInfo> methods = clazz.getMethods();

    //     // Passo 1: Encontrar método com nome e parâmetros exatos (mesma ordem e tipos)
    //     Optional<MethodInfo> exactMatch = methods.stream()
    //         .filter(m -> m.getName().equals(methodName) && m.getParameterTypes().equals(paramTypes))
    //         .findFirst();
    //     if (exactMatch.isPresent()) {
    //         return exactMatch;
    //     }

    //     // Passo 2: Encontrar método com nome igual e os mesmos parâmetros, mas em ordem diferente
    //     Optional<MethodInfo> unorderedMatch = methods.stream()
    //         .filter(m -> m.getName().equals(methodName) && areParameterTypesMatchingUnordered(paramTypes, m.getParameterTypes()))
    //         .findFirst();
    //     if (unorderedMatch.isPresent()) {
    //         return unorderedMatch;
    //     }

    //     // Passo 3: Encontrar método com nome igual e mesma quantidade de parâmetros
    //     Optional<MethodInfo> paramCountMatch = methods.stream()
    //         .filter(m -> m.getName().equals(methodName) && countParameters(paramTypes) == countParameters(m.getParameterTypes()))
    //         .findFirst();
    //     if (paramCountMatch.isPresent()) {
    //         return paramCountMatch;
    //     }

    //     // Passo 4: Encontrar método apenas com nome igual
    //     return methods.stream()
    //         .filter(m -> m.getName().equals(methodName))
    //         .findFirst();
    // }

    // // Verifica se os tipos de parâmetros são os mesmos, ignorando a ordem
    // private static boolean areParameterTypesMatchingUnordered(String paramTypes1, String paramTypes2) {
    //     List<String> params1 = splitParameterTypes(paramTypes1);
    //     List<String> params2 = splitParameterTypes(paramTypes2);
    //     return params1.size() == params2.size() && params1.containsAll(params2);
    // }

    // // Conta o número de parâmetros em uma string de tipos de parâmetro
    // private static int countParameters(String paramTypes) {
    //     return splitParameterTypes(paramTypes).size();
    // }

    // // Divide a string de tipos de parâmetros em uma lista
    // private static List<String> splitParameterTypes(String paramTypes) {
    //     return paramTypes.isEmpty() ? List.of() : List.of(paramTypes.split(",\\s*"));
    // }

    // public static String formatParameterTypes(Class<?>[] parameterTypes) {
    //     return Arrays.stream(parameterTypes).map(Class::getSimpleName).collect(Collectors.joining(", "));
    // }

    // public static int calculateGrade(boolean... conditions) {
    //     int correctCount = 0;
    //     for (boolean condition : conditions) {
    //         if (condition) {
    //             correctCount++;
    //         }
    //     }
    //     return Math.round((correctCount / (float) conditions.length) * 100);
    // }

    // public static boolean areParameterTypesMatching(Class<?>[] paramTypes1, Class<?>[] paramTypes2) {
    //     return Arrays.equals(paramTypes1, paramTypes2);
    // }