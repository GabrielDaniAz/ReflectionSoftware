package com.reflectionsoftware.service.reflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.reflectionsoftware.model.Professor;
import com.reflectionsoftware.service.compilation.CompilationService.Exercise;

public class CorrectionService {
    private static Professor professor;

    public CorrectionService(Professor professor) {
        CorrectionService.professor = professor;
    }

    public ReflectionResult correctClasses(List<Class<?>> studentClasses) {
        ReflectionResult reflectionResult = new ReflectionResult();

        for (Exercise exercise : professor.getExercises()) {
            StepResult stepResult = correctStep(exercise, studentClasses);
            reflectionResult.addStepResult(stepResult);
        }

        return reflectionResult;
    }

    private StepResult correctStep(Exercise exercise, List<Class<?>> studentClasses) {
        StepResult stepResult = new StepResult(exercise.getStep());

        for (Class<?> professorClazz : exercise.getClasses()) {
            Class<?> studentClass = getStudentClass(professorClazz, studentClasses);

            if (studentClass != null) {
                ComparisonResult comparisonResult = compareClasses(professorClazz, studentClass);
                stepResult.addComparisonResult(comparisonResult);
            } else {
                stepResult.addMissingClass(professorClazz.getSimpleName());
            }
        }

        return stepResult;
    }

    private Class<?> getStudentClass(Class<?> professorClazz, List<Class<?>> studentClasses) {
        return studentClasses.stream()
                .filter(clazz -> clazz.getSimpleName().equals(professorClazz.getSimpleName()))
                .findFirst()
                .orElse(null);
    }

    private ComparisonResult compareClasses(Class<?> professorClazz, Class<?> studentClazz) {
        List<ComparisonField> fieldComparisons = compareFields(professorClazz, studentClazz);
        List<ComparisonMethod> methodComparisons = compareMethods(professorClazz, studentClazz);
        List<ComparisonConstructor> constructorComparisons = compareConstructors(professorClazz, studentClazz);

        String comparisonDetail = generateComparisonDetail(fieldComparisons, methodComparisons, constructorComparisons);

        return new ComparisonResult(professorClazz.getSimpleName(), comparisonDetail,
                                    fieldComparisons, methodComparisons, constructorComparisons);
    }

    private List<ComparisonField> compareFields(Class<?> professorClazz, Class<?> studentClazz) {
        List<ComparisonField> fieldComparisons = new ArrayList<>();
    
        for (Field professorField : professorClazz.getDeclaredFields()) {
            String fieldName = professorField.getName();
            String professorFieldType = professorField.getType().getSimpleName();
    
            try {
                Field studentField = studentClazz.getDeclaredField(fieldName);
                String studentFieldType = studentField.getType().getSimpleName();
    
                boolean isSameType = professorField.getType().equals(studentField.getType());
                String detail = isSameType 
                    ? "Atributo correto: " + fieldName + " (tipo: " + professorFieldType + ")"
                    : "Tipo incorreto para o atributo '" + fieldName + "'. Esperado: " + professorFieldType + ", mas encontrado: " + studentFieldType;
    
                fieldComparisons.add(new ComparisonField(fieldName, isSameType, detail));
            } catch (NoSuchFieldException e) {
                String detail = "Atributo ausente: " + fieldName + " (tipo esperado: " + professorFieldType + ")";
                fieldComparisons.add(new ComparisonField(fieldName, false, detail));
            }
        }
    
        return fieldComparisons;
    }

    private List<ComparisonMethod> compareMethods(Class<?> professorClazz, Class<?> studentClazz) {
        List<ComparisonMethod> methodComparisons = new ArrayList<>();
    
        for (Method professorMethod : professorClazz.getDeclaredMethods()) {
            String methodName = professorMethod.getName();
            String professorReturnTypeName = professorMethod.getReturnType().getSimpleName();  // Nome simples do tipo de retorno
            Class<?>[] professorParamTypes = professorMethod.getParameterTypes();
    
            try {
                Method studentMethod = studentClazz.getDeclaredMethod(methodName, professorParamTypes);
                String studentReturnTypeName = studentMethod.getReturnType().getSimpleName();  // Nome simples do tipo de retorno
    
                // Compara apenas os nomes simples dos tipos de retorno
                boolean isSameReturnType = professorReturnTypeName.equals(studentReturnTypeName);
                String detail = isSameReturnType
                    ? "Método correto: " + methodName + " (retorno: " + professorReturnTypeName + ", parâmetros: " + formatParameterTypes(professorParamTypes) + ")"
                    : "Incompatibilidade no tipo de retorno para o método '" + methodName + "'. Esperado: " + professorReturnTypeName + ", mas encontrado: " + studentReturnTypeName;
    
                methodComparisons.add(new ComparisonMethod(methodName, isSameReturnType, detail));
            } catch (NoSuchMethodException e) {
                String detail = "Método ausente: " + methodName + " (retorno esperado: " + professorReturnTypeName + ", parâmetros: " + formatParameterTypes(professorParamTypes) + ")";
                methodComparisons.add(new ComparisonMethod(methodName, false, detail));
            }
        }
    
        return methodComparisons;
    }

    private List<ComparisonConstructor> compareConstructors(Class<?> professorClazz, Class<?> studentClazz) {
        List<ComparisonConstructor> constructorComparisons = new ArrayList<>();
    
        for (Constructor<?> professorConstructor : professorClazz.getDeclaredConstructors()) {
            Class<?>[] professorParamTypes = professorConstructor.getParameterTypes();
    
            try {
                studentClazz.getDeclaredConstructor(professorParamTypes);
                String detail = "Construtor encontrado com parâmetros: (" + formatParameterTypes(professorParamTypes) + ")";
                constructorComparisons.add(new ComparisonConstructor(true, detail));
            } catch (NoSuchMethodException e) {
                String detail = "Construtor ausente. Esperado com parâmetros: (" + formatParameterTypes(professorParamTypes) + ")";
                constructorComparisons.add(new ComparisonConstructor(false, detail));
            }
        }
    
        return constructorComparisons;
    }

    // Método auxiliar para formatar os tipos de parâmetros
    private String formatParameterTypes(Class<?>[] parameterTypes) {
        if (parameterTypes.length == 0) return "nenhum";
        return Arrays.stream(parameterTypes)
                    .map(Class::getSimpleName)
                    .collect(Collectors.joining(", "));
    }
    

    private String generateComparisonDetail(List<ComparisonField> fields, List<ComparisonMethod> methods, List<ComparisonConstructor> constructors) {
        StringBuilder detail = new StringBuilder("Detalhes da comparação:\n");

        detail.append("Fields:\n");
        fields.forEach(f -> detail.append("- ").append(f.getDetail()).append("\n"));

        detail.append("Methods:\n");
        methods.forEach(m -> detail.append("- ").append(m.getDetail()).append("\n"));

        detail.append("Constructors:\n");
        constructors.forEach(c -> detail.append("- ").append(c.getDetail()).append("\n"));

        return detail.toString();
    }

    public static class ReflectionResult {
        private final List<StepResult> stepResults = new ArrayList<>();

        public void addStepResult(StepResult stepResult) { stepResults.add(stepResult); }
        public List<StepResult> getStepResults() { return stepResults; }

        public void printDetailedResults() {
            for (StepResult stepResult : stepResults) {
                System.out.println("Resultados para o Step " + stepResult.getStepName() + ":");
                for (ComparisonResult comparisonResult : stepResult.getComparisonResults()) {
                    System.out.println("- Comparação da classe " + comparisonResult.getClassName() + ": " + comparisonResult.getDetail());
                }
                if (!stepResult.getMissingClasses().isEmpty()) {
                    System.out.println("- Classes faltando: " + String.join(", ", stepResult.getMissingClasses()));
                }
            }
        }
    }

    public static class StepResult {
        private final String stepName;
        private final List<ComparisonResult> comparisonResults = new ArrayList<>();
        private final List<String> missingClasses = new ArrayList<>();

        public StepResult(String stepName) { this.stepName = stepName; }
        public void addComparisonResult(ComparisonResult comparisonResult) { comparisonResults.add(comparisonResult); }
        public void addMissingClass(String className) { missingClasses.add(className); }
        public String getStepName() { return stepName; }
        public List<ComparisonResult> getComparisonResults() { return comparisonResults; }
        public List<String> getMissingClasses() { return missingClasses; }
    }

    public static class ComparisonResult {
        private final String className;
        private final String detail;
        private final List<ComparisonField> fields;
        private final List<ComparisonMethod> methods;
        private final List<ComparisonConstructor> constructors;

        public ComparisonResult(String className, String detail,
                                List<ComparisonField> fields, List<ComparisonMethod> methods, List<ComparisonConstructor> constructors) {
            this.className = className;
            this.detail = detail;
            this.fields = fields;
            this.methods = methods;
            this.constructors = constructors;
        }

        public String getClassName() { return className; }
        public String getDetail() { return detail; }
        public List<ComparisonField> getComparisionFields() { return fields; }
        public List<ComparisonMethod> getComparisionMethods() { return methods; }
        public List<ComparisonConstructor> getComparisionConstructors() { return constructors; }
    }

    public static class ComparisonField {
        private final String fieldName;
        private final boolean isEqual;
        private final String detail;

        public ComparisonField(String fieldName, boolean isEqual, String detail) {
            this.fieldName = fieldName;
            this.isEqual = isEqual;
            this.detail = detail;
        }

        public String getFieldName() { return fieldName; }
        public boolean isEqual() { return isEqual; }
        public String getDetail() { return detail; }
    }

    public static class ComparisonMethod {
        private final String methodName;
        private final boolean isEqual;
        private final String detail;

        public ComparisonMethod(String methodName, boolean isEqual, String detail) {
            this.methodName = methodName;
            this.isEqual = isEqual;
            this.detail = detail;
        }

        public String getMethodName() { return methodName; }
        public boolean isEqual() { return isEqual; }
        public String getDetail() { return detail; }
    }

    public static class ComparisonConstructor {
        private final boolean isEqual;
        private final String detail;

        public ComparisonConstructor(boolean isEqual, String detail) {
            this.isEqual = isEqual;
            this.detail = detail;
        }

        public boolean isEqual() { return isEqual; }
        public String getDetail() { return detail; }
    }
}

