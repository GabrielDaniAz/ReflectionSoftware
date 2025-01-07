package com.javacorrige.util.reflection.element;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.javacorrige.util.reflection.NameSimilarityCalculator;

public class ElementComparer {

    /**
     * Compara dois elementos pelo nome.
     */
    public static boolean hasSameName(Object templateElement, Object studentElement, double similarityThreshold) {
        String templateName = getElementName(templateElement);
        String studentName = getElementName(studentElement);

        return templateName != null && studentName != null && 
                (NameSimilarityCalculator.calculate(templateName, studentName) >= similarityThreshold);
    }

    /**
     * Compara a similaridade entre elementos com base em critérios avançados.
     */
    public static boolean areSimilar(Object templateElement, Object studentElement, double similarityThreshold) {
        if (!hasSameName(templateElement, studentElement, similarityThreshold)) return false;
        
        if (templateElement instanceof Method templateMethod && studentElement instanceof Method studentMethod) {
            return templateMethod.getReturnType().equals(studentMethod.getReturnType()) &&
                   areParametersEqual(templateMethod.getParameterTypes(), studentMethod.getParameterTypes());
        }

        if (templateElement instanceof Constructor<?> templateConstructor && studentElement instanceof Constructor<?> studentConstructor) {
            return areParametersEqual(templateConstructor.getParameterTypes(), studentConstructor.getParameterTypes());
        }

        return templateElement instanceof Field && studentElement instanceof Field;
    }

    private static boolean areParametersEqual(Class<?>[] params1, Class<?>[] params2) {
        if (params1.length != params2.length) return false;
        return Arrays.equals(params1, params2);
    }

    private static String getElementName(Object element) {
        if (element instanceof Field field) {
            return field.getName();
        } else if (element instanceof Method method) {
            return method.getName();
        } else if (element instanceof Constructor<?> constructor) {
            return constructor.getName();
        }
        return null;
    }
}
