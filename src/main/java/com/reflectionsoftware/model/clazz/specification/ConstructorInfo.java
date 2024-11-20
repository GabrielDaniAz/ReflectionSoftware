package com.reflectionsoftware.model.clazz.specification;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ConstructorInfo {
    private final String visibility;
    private final String modifiers;
    private final String name;
    private final String parameterTypes;

    public ConstructorInfo(Constructor<?> constructor) {
        this.visibility = convertVisibility(constructor.getModifiers());
        this.modifiers = Modifier.toString(constructor.getModifiers()).replace(visibility, "").trim();
        this.name = constructor.getName();
        this.parameterTypes = convertParameterTypes(constructor.getParameterTypes());
    }

    public String getVisibility() { return visibility; }
    public String getModifiers() { return modifiers; }
    public String getName() { return name; }
    public String getParameterTypes() { return parameterTypes; }

    private String convertParameterTypes(Class<?>[] parameterTypes) {
        return Stream.of(parameterTypes)
            .map(Class::getSimpleName)
            .collect(Collectors.joining(", "));
    }

    private String convertVisibility(int modifiers) {
        if (Modifier.isPublic(modifiers)) {
            return "public";
        } else if (Modifier.isPrivate(modifiers)) {
            return "private";
        } else if (Modifier.isProtected(modifiers)) {
            return "protected";
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s %s(%s)", 
            visibility, 
            modifiers, 
            name, 
            parameterTypes
        ).replaceAll("\\s+", " ").trim();
    }
}
