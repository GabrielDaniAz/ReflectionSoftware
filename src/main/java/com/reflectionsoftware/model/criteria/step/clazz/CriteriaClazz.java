package com.reflectionsoftware.model.criteria.step.clazz;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.annotations.SerializedName;
import com.reflectionsoftware.model.criteria.step.clazz.specification.CriteriaConstructor;
import com.reflectionsoftware.model.criteria.step.clazz.specification.CriteriaField;
import com.reflectionsoftware.model.criteria.step.clazz.specification.CriteriaMethod;

public class CriteriaClazz {

    @SerializedName("nome_da_classe")
    private String clazzName;

    @SerializedName("especificacao_atributos")
    private String attributeSpecification;

    @SerializedName("especificacao_construtores")
    private String constructorSpecification;

    @SerializedName("especificacao_metodos")
    private String methodSpecification;

    @SerializedName("atributos")
    private List<CriteriaField> fields;

    @SerializedName("construtores")
    private List<CriteriaConstructor> constructors;

    @SerializedName("metodos")
    private List<CriteriaMethod> methods;

    public CriteriaClazz() {
        fields = new ArrayList<>();
        constructors = new ArrayList<>();
        methods = new ArrayList<>();
    }

    public String getClazzName() {
        return (clazzName != null) ? clazzName : "Classe não definida";
    }

    public String getAttributeSpecification() {
        return (attributeSpecification != null) ? attributeSpecification : "exata";
    }

    public String getConstructorSpecification() {
        return (constructorSpecification != null) ? constructorSpecification : "exata";
    }

    public String getMethodSpecification() {
        return (methodSpecification != null) ? methodSpecification : "exata";
    }

    public List<CriteriaField> getFields() {
        return Collections.unmodifiableList(fields);
    }

    public List<CriteriaConstructor> getConstructors() {
        return Collections.unmodifiableList(constructors);
    }

    public List<CriteriaMethod> getMethods() {
        return Collections.unmodifiableList(methods);
    }

    public void mergeWith(CriteriaClazz newClazz) {
        // Atualiza atributos
        for (CriteriaField newField : newClazz.getFields()) {
            if (!fieldExists(newField)) {
                fields.add(newField);
            }
        }

        // Atualiza construtores
        for (CriteriaConstructor newConstructor : newClazz.getConstructors()) {
            if (!constructorExists(newConstructor)) {
                constructors.add(newConstructor);
            }
        }

        // Atualiza métodos
        for (CriteriaMethod newMethod : newClazz.getMethods()) {
            if (!methodExists(newMethod)) {
                methods.add(newMethod);
            }
        }
    }

    private boolean fieldExists(CriteriaField field) {
        return fields.stream()
                .anyMatch(existingField -> existingField.getName().equals(field.getName()));
    }

    private boolean constructorExists(CriteriaConstructor constructor) {
        return constructors.stream()
                .anyMatch(existingConstructor -> existingConstructor.getParameters().equals(constructor.getParameters()));
    }

    private boolean methodExists(CriteriaMethod method) {
        return methods.stream()
                .anyMatch(existingMethod -> existingMethod.getName().equals(method.getName())
                        && existingMethod.getParameters().equals(method.getParameters()));
    }

    @Override
    public String toString() {
        return "{ \"nome_da_classe\": \"" + getClazzName() + "\", " +
                "\"especificacao_atributos\": \"" + getAttributeSpecification() + "\", " +
                "\"especificacao_construtores\": \"" + getConstructorSpecification() + "\", " +
                "\"especificacao_metodos\": \"" + getMethodSpecification() + "\", " +
                "\"atributos\": " + getFields() + ", " +
                "\"construtores\": " + getConstructors() + ", " +
                "\"metodos\": " + getMethods() + " }";
    }
}
