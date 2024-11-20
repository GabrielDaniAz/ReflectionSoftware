package com.reflectionsoftware.model.clazz.specification;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodInfo {
    private final String visibility;
    private final String modifiers;
    private final String returnType;
    private final String name;
    private final String parameterTypes;

    public MethodInfo(Method method) {
        this.visibility = convertVisibility(method.getModifiers());
        this.modifiers = Modifier.toString(method.getModifiers()).replace(visibility, "").trim();
        this.returnType = method.getReturnType().getSimpleName();
        this.name = method.getName();
        this.parameterTypes = convertParameterTypes(method.getParameterTypes());
    }

    public String getVisibility() { return visibility; }
    public String getModifiers() { return modifiers; }
    public String getReturnType() { return returnType; }
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
        // Formata o método como "private static void methodName(String param1, int param2)"
        return String.format("%s %s %s %s(%s)", 
            visibility, 
            modifiers, 
            returnType, 
            name, 
            parameterTypes
        ).replaceAll("\\s+", " ").trim();
    }
}
