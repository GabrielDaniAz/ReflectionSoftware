package com.reflectionsoftware.service.reflection;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.reflectionsoftware.model.clazz.ClassInfo;
import com.reflectionsoftware.model.clazz.specification.ConstructorInfo;
import com.reflectionsoftware.model.clazz.specification.FieldInfo;
import com.reflectionsoftware.model.clazz.specification.MethodInfo;

public class ComparisonUtils {

    public static Optional<FieldInfo> getFieldByName(String fieldName, List<FieldInfo> fields) {
        return fields.stream().filter(field -> field.getName().equals(fieldName)).findFirst();
    }

    public static Optional<ClassInfo> getClassByName(String className, List<ClassInfo> classes) {
        return classes.stream().filter(clazz -> clazz.getClassName().equals(className)).findFirst();
    }

    public static Optional<ConstructorInfo> findConstructor(ClassInfo clazz, String paramTypes) {
        List<ConstructorInfo> constructors = clazz.getConstructors();

        // Passo 1: Encontrar construtor com parâmetros exatos (mesma ordem e tipos)
        Optional<ConstructorInfo> exactMatch = constructors.stream()
            .filter(c -> c.getParameterTypes().equals(paramTypes))
            .findFirst();
        if (exactMatch.isPresent()) {
            return exactMatch;
        }

        // Passo 2: Encontrar construtor com os mesmos parâmetros, mas em ordem diferente
        Optional<ConstructorInfo> unorderedMatch = constructors.stream()
            .filter(c -> areParameterTypesMatchingUnordered(paramTypes, c.getParameterTypes()))
            .findFirst();
        if (unorderedMatch.isPresent()) {
            return unorderedMatch;
        }

        // Passo 3: Encontrar construtor com a mesma quantidade de parâmetros
        Optional<ConstructorInfo> paramCountMatch = constructors.stream()
            .filter(c -> countParameters(paramTypes) == countParameters(c.getParameterTypes()))
            .findFirst();
        if (paramCountMatch.isPresent()) {
            return paramCountMatch;
        }

        // Passo 4: Retornar qualquer construtor (apenas para casos em que nenhum critério acima é atendido)
        return constructors.stream().findFirst();
    }


    public static Optional<MethodInfo> findMethod(ClassInfo clazz, String methodName, String paramTypes) {
        List<MethodInfo> methods = clazz.getMethods();

        // Passo 1: Encontrar método com nome e parâmetros exatos (mesma ordem e tipos)
        Optional<MethodInfo> exactMatch = methods.stream()
            .filter(m -> m.getName().equals(methodName) && m.getParameterTypes().equals(paramTypes))
            .findFirst();
        if (exactMatch.isPresent()) {
            return exactMatch;
        }

        // Passo 2: Encontrar método com nome igual e os mesmos parâmetros, mas em ordem diferente
        Optional<MethodInfo> unorderedMatch = methods.stream()
            .filter(m -> m.getName().equals(methodName) && areParameterTypesMatchingUnordered(paramTypes, m.getParameterTypes()))
            .findFirst();
        if (unorderedMatch.isPresent()) {
            return unorderedMatch;
        }

        // Passo 3: Encontrar método com nome igual e mesma quantidade de parâmetros
        Optional<MethodInfo> paramCountMatch = methods.stream()
            .filter(m -> m.getName().equals(methodName) && countParameters(paramTypes) == countParameters(m.getParameterTypes()))
            .findFirst();
        if (paramCountMatch.isPresent()) {
            return paramCountMatch;
        }

        // Passo 4: Encontrar método apenas com nome igual
        return methods.stream()
            .filter(m -> m.getName().equals(methodName))
            .findFirst();
    }

    // Verifica se os tipos de parâmetros são os mesmos, ignorando a ordem
    private static boolean areParameterTypesMatchingUnordered(String paramTypes1, String paramTypes2) {
        List<String> params1 = splitParameterTypes(paramTypes1);
        List<String> params2 = splitParameterTypes(paramTypes2);
        return params1.size() == params2.size() && params1.containsAll(params2);
    }

    // Conta o número de parâmetros em uma string de tipos de parâmetro
    private static int countParameters(String paramTypes) {
        return splitParameterTypes(paramTypes).size();
    }

    // Divide a string de tipos de parâmetros em uma lista
    private static List<String> splitParameterTypes(String paramTypes) {
        return paramTypes.isEmpty() ? List.of() : List.of(paramTypes.split(",\\s*"));
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
}
