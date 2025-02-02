package com.javacorrige.util.reflection.element;

import java.util.HashMap;
import java.util.List;

public class ElementMapper {

    /**
     * Mapeia os elementos entre duas classes com base em critérios.
     */
    public static HashMap<Object, Object> mapElements(Class<?> templateClass, Class<?> studentClass) {
        HashMap<Object, Object> elementMap = new HashMap<>();

        List<Object> templateElements = ElementExtractor.extractElements(templateClass);
        List<Object> studentElements = ElementExtractor.extractElements(studentClass);

        List<Object> unmatchedStudents = studentElements;

        double similarityThreshold = ElementUtils.getSimilarityThreshold(templateClass);

        for (Object templateElement : templateElements) {
            Object matchedElement = unmatchedStudents.stream()
            .filter(studentElement -> ElementComparer.areSimilar(templateElement, studentElement, similarityThreshold))
            .findFirst()
            .orElse(null);

            if (matchedElement == null) {
                matchedElement = unmatchedStudents.stream()
                .filter(studentElement -> ElementComparer.hasSameName(templateElement, studentElement, similarityThreshold))
                .findFirst()
                .orElse(null);
            }

            elementMap.put(templateElement, matchedElement);
            unmatchedStudents.remove(matchedElement);
        }

        // unmatchedStudents.forEach(unmatched -> elementMap.put(null, unmatched));
        elementMap.put(null, unmatchedStudents);

        return elementMap;
    }

}
