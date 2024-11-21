package com.reflectionsoftware.model.result.correction.exercise.clazz;

import java.util.List;

import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.ConstructorCorrection;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.FieldCorrection;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.MethodCorrection;

public class ClassCorrection {

    private String className;

    private final List<ConstructorCorrection> constructorsCorrection;
    private final List<FieldCorrection> fieldsCorrection;
    private final List<MethodCorrection> methodsCorrection;

    public ClassCorrection(String className, List<ConstructorCorrection> constructorsCorrection, 
            List<FieldCorrection> fieldsCorrection, List<MethodCorrection> methodsCorrection) {
        this.className = className;
        this.constructorsCorrection = constructorsCorrection;
        this.fieldsCorrection = fieldsCorrection;
        this.methodsCorrection = methodsCorrection;
    }

    public String getClassName() { return className; }
    public List<ConstructorCorrection> getConstructorsCorrection() { return constructorsCorrection; }
    public List<FieldCorrection> getFieldsCorrection() { return fieldsCorrection; }
    public List<MethodCorrection> getMethodsCorrection() { return methodsCorrection; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Correção para classe: ").append(className).append("\n");

        sb.append("Construtores:\n");
        if (constructorsCorrection.isEmpty()) {
            sb.append("  Nenhum construtor para correção.\n");
        } else {
            for (ConstructorCorrection correction : constructorsCorrection) {
                sb.append("  - ").append(correction.toString()).append("\n");
            }
        }

        sb.append("Atributos:\n");
        if (fieldsCorrection.isEmpty()) {
            sb.append("  Nenhum atributo para correção.\n");
        } else {
            for (FieldCorrection correction : fieldsCorrection) {
                sb.append("  - ").append(correction.toString()).append("\n");
            }
        }

        sb.append("Métodos:\n");
        if (methodsCorrection.isEmpty()) {
            sb.append("  Nenhum método para correção.\n");
        } else {
            for (MethodCorrection correction : methodsCorrection) {
                sb.append("  - ").append(correction.toString()).append("\n");
            }
        }

        return sb.toString();
    }

}

