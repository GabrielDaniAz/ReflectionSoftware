package com.reflectionsoftware.model.result.correction.exercise.clazz;

import java.util.ArrayList;
import java.util.List;

import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.ConstructorCorrection;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.FieldCorrection;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.MethodCorrection;

public class ClassCorrection {

    private Class<?> templateClass;
    private Class<?> studentClass;

    private List<ConstructorCorrection> constructorCorrections;
    private List<FieldCorrection> fieldCorrections;
    private List<MethodCorrection> methodCorrections;

    public ClassCorrection(Class<?> templateClass, Class<?> studentClass) {
        this.templateClass = templateClass;
        this.studentClass = studentClass;

        this.constructorCorrections = new ArrayList<>();
        this.fieldCorrections = new ArrayList<>();
        this.methodCorrections = new ArrayList<>();
    }

    public Class<?> getTemplateClass(){ return templateClass; }
    public Class<?> getStudentClass(){ return studentClass; }

    public List<ConstructorCorrection> getConstructorCorrections(){ return constructorCorrections; }
    public List<FieldCorrection> getFieldCorrections(){ return fieldCorrections; }
    public List<MethodCorrection> getMethodCorrections(){ return methodCorrections; }

    public void addConstructorCorrection(ConstructorCorrection constructorCorrection){ this.constructorCorrections.add(constructorCorrection); }
    public void addFieldCorrection(FieldCorrection fieldCorrection){ this.fieldCorrections.add(fieldCorrection); }
    public void addMethodCorrection(MethodCorrection methodCorrection){ this.methodCorrections.add(methodCorrection); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Correção da Classe\n");
        sb.append("Classe do Template: ").append(templateClass != null ? templateClass.getSimpleName() : "Não definida").append("\n");
        sb.append("Classe do Estudante: ").append(studentClass != null ? studentClass.getSimpleName() : "Não definida").append("\n");
        
        sb.append("\nConstrutores:\n");
        if (constructorCorrections == null || constructorCorrections.isEmpty()) {
            sb.append("  Nenhuma correção de construtor.\n");
        } else {
            for (ConstructorCorrection correction : constructorCorrections) {
                sb.append("  ").append(correction).append("\n");
            }
        }

        sb.append("\nAtributos:\n");
        if (fieldCorrections == null || fieldCorrections.isEmpty()) {
            sb.append("  Nenhuma correção de campo.\n");
        } else {
            for (FieldCorrection correction : fieldCorrections) {
                sb.append("  ").append(correction).append("\n");
            }
        }

        sb.append("\nMétodos:\n");
        if (methodCorrections == null || methodCorrections.isEmpty()) {
            sb.append("  Nenhuma correção de método.\n");
        } else {
            for (MethodCorrection correction : methodCorrections) {
                sb.append("  ").append(correction).append("\n");
            }
        }

        return sb.toString();
    }

}

