package com.reflectionsoftware.util.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ElementMapper {

    public static HashMap<Object, Object> mapElements(
            Class<?> templateClass, Class<?> studentClass) {

        HashMap<Object, Object> elementMap = new HashMap<>();

        // Obtém todos os elementos do template
        List<Object> templateElements = new ArrayList<>();
        if (templateClass != null) {
            templateElements.addAll(List.of(templateClass.getDeclaredFields()));
            templateElements.addAll(List.of(templateClass.getDeclaredMethods()));
            templateElements.addAll(List.of(templateClass.getDeclaredConstructors()));
        }

        // Obtém todos os elementos do estudante
        List<Object> studentElements = new ArrayList<>();
        if (studentClass != null) {
            studentElements.addAll(List.of(studentClass.getDeclaredFields()));
            studentElements.addAll(List.of(studentClass.getDeclaredMethods()));
            studentElements.addAll(List.of(studentClass.getDeclaredConstructors()));
        }

        List<Object> unmatchedStudents = new ArrayList<>(studentElements);

        // Mapeia elementos do template com os do estudante
        for (Object templateElement : templateElements) {
            Object matchedElement = unmatchedStudents.stream()
                    .filter(studentElement -> areElementsEqual(templateElement, studentElement))
                    .findFirst()
                    .orElse(null);

            elementMap.put(templateElement, matchedElement);
            unmatchedStudents.remove(matchedElement);
        }

        // Adiciona elementos restantes do estudante como não correspondidos
        unmatchedStudents.forEach(unmatched -> elementMap.put(null, unmatched));

        return elementMap;
    }

    /**
     * Verifica se dois elementos são iguais com base no tipo e em suas características.
     */
    private static boolean areElementsEqual(Object templateElement, Object studentElement) {
        if (templateElement instanceof Field templateField && studentElement instanceof Field studentField) {
            return templateField.getName().equals(studentField.getName());
        }

        if (templateElement instanceof Method templateMethod && studentElement instanceof Method studentMethod) {
            return templateMethod.getName().equals(studentMethod.getName()) &&
                   templateMethod.getReturnType().equals(studentMethod.getReturnType()) &&
                   areParametersEqual(templateMethod.getParameterTypes(), studentMethod.getParameterTypes());
        }

        if (templateElement instanceof Constructor<?> templateConstructor && studentElement instanceof Constructor<?> studentConstructor) {
            return templateConstructor.getName().equals(studentConstructor.getName()) &&
                   areParametersEqual(templateConstructor.getParameterTypes(), studentConstructor.getParameterTypes());
        }

        return false;
    }

    /**
     * Compara os parâmetros de dois elementos (métodos ou construtores).
     */
    private static boolean areParametersEqual(Class<?>[] params1, Class<?>[] params2) {
        if (params1.length != params2.length) {
            return false;
        }
        for (int i = 0; i < params1.length; i++) {
            if (!params1[i].equals(params2[i])) {
                return false;
            }
        }
        return true;
    }
}
