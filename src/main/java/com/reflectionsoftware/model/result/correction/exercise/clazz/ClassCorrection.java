package com.reflectionsoftware.model.result.correction.exercise.clazz;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.reflectionsoftware.model.result.correction.SpecificationElement;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.ConstructorCorrection;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.FieldCorrection;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.MethodCorrection;
import com.reflectionsoftware.util.reflection.element.ElementMapper;
import com.reflectionsoftware.util.reflection.element.ElementUtils;

public class ClassCorrection {
    private final Class<?> template;
    private final Class<?> student;

    private final List<SpecificationElement<?>> elements;

    public ClassCorrection(Class<?> template, Class<?> student) {
        this.template = template;
        this.student = student;

        this.elements = initializeElements(template, student);
    }

    private List<SpecificationElement<?>> initializeElements(Class<?> template, Class<?> student) {
        List<SpecificationElement<?>> elementList = new ArrayList<>();

        HashMap<Object, Object> mappedElements = ElementMapper.mapElements(template, student);

        mappedElements.forEach((templateElement, studentElement) -> {
            if (templateElement instanceof Field || studentElement instanceof Field) {
                elementList.add(new FieldCorrection(
                        (Field) templateElement, 
                        (Field) studentElement,
                        allFieldsPrivate()));
            } else if (templateElement instanceof Method || studentElement instanceof Method) {
                elementList.add(new MethodCorrection(
                        (Method) templateElement, 
                        (Method) studentElement));
            } else if (templateElement instanceof Constructor<?> || studentElement instanceof Constructor<?>) {
                elementList.add(new ConstructorCorrection(
                        (Constructor<?>) templateElement, 
                        (Constructor<?>) studentElement));
            }
        });

        return elementList;
    }

    public Class<?> getTemplate() { return template; }
    public Class<?> getStudent() { return student; }
    public List<SpecificationElement<?>> getElements() { return elements; }
    
    public List<SpecificationElement<?>> getCorrectedElements() {
        List<SpecificationElement<?>> corrected = new ArrayList<>();
        for (SpecificationElement<?> e : elements) {
            if (!e.hasTemplate() || !e.hasStudent()) continue;
            corrected.add(e);
        }
        return corrected;
    }

    public double getGrade() {
        return elements.stream()
                .mapToDouble(SpecificationElement::getGrade)
                .sum();
    }

    public double getObtainedGrade() {
        return elements.stream()
                .mapToDouble(SpecificationElement::getObtainedGrade)
                .sum();
    }

    public boolean allFieldsPrivate() {
        return ElementUtils.allFieldsPrivate(template);
    }

    public List<SpecificationElement<?>> getMissingElements() {
        List<SpecificationElement<?>> missing = new ArrayList<>();

        for (SpecificationElement<?> element : elements) {
            if (!element.hasStudent() && element.hasTemplate()) {
                missing.add(element);
            }
        }

        return missing;
    }
}
