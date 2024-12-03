package com.reflectionsoftware.util.reflection.element;

import java.util.ArrayList;
import java.util.List;

public class ElementExtractor {

    public static List<Object> extractElements(Class<?> clazz) {
        List<Object> elements = new ArrayList<>();
        if (clazz != null) {
            elements.addAll(List.of(clazz.getDeclaredFields()));
            elements.addAll(List.of(clazz.getDeclaredMethods()));
            elements.addAll(List.of(clazz.getDeclaredConstructors()));
        }
        return elements;
    }
}
