package com.reflectionsoftware.model.clazz.specification;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldInfo {
    private final String visibility;
    private final String modifiers;
    private final String type;
    private final String name;

    public FieldInfo(Field field) {
        this.visibility = convertToVisibility(field.getModifiers());
        this.modifiers = Modifier.toString(field.getModifiers()).replace(visibility, "").trim();
        this.type = field.getType().getSimpleName();
        this.name = field.getName();
    }

    public String getVisibility() { return visibility; }
    public String getModifiers() { return modifiers; }
    public String getType() { return type; }
    public String getName() { return name; }

    private String convertToVisibility(int modifiers) {
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
        return String.format("%s %s %s %s", 
            visibility, 
            modifiers, 
            type, 
            name
        ).replaceAll("\\s+", " ").trim();
    }
}
