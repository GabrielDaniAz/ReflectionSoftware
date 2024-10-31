package com.reflectionsoftware.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.io.File;
import java.util.Arrays;

import com.reflectionsoftware.model.Clazz;
import com.reflectionsoftware.model.criteria.Criteria;
import com.reflectionsoftware.model.criteria.step.CriteriaStep;
import com.reflectionsoftware.model.criteria.step.clazz.CriteriaClazz;
import com.reflectionsoftware.model.criteria.step.clazz.specification.CriteriaConstructor;
import com.reflectionsoftware.model.criteria.step.clazz.specification.CriteriaField;
import com.reflectionsoftware.model.criteria.step.clazz.specification.CriteriaMethod;
import com.reflectionsoftware.model.result.reflectionResult.ReflectionResult;
import com.reflectionsoftware.model.result.reflectionResult.step.StepResult;
import com.reflectionsoftware.model.result.reflectionResult.step.clazz.ClazzResult;
import com.reflectionsoftware.model.result.reflectionResult.step.clazz.specification.ConstructorResult;
import com.reflectionsoftware.model.result.reflectionResult.step.clazz.specification.FieldResult;
import com.reflectionsoftware.model.result.reflectionResult.step.clazz.specification.MethodResult;

public class ReflectionService {

    private final Criteria criteria;

    public ReflectionService(Criteria criteria) {
        this.criteria = criteria;
    }

    public ReflectionResult analyzeReflection(List<File> compiledFiles, File outputDir, String studentName) {
        try {
            List<Class<?>> classes = ClassFileLoader.loadClasses(compiledFiles, outputDir);
            List<Clazz> clazzes = classes.stream().map(Clazz::new).collect(Collectors.toList());
            List<StepResult> stepResults = evaluateSteps(clazzes);
            return new ReflectionResult(studentName, stepResults);
        } catch (Exception e) {
            System.err.println("Erro no loadClasses: " + e.getMessage());
            return null;
        }
    }

    private List<StepResult> evaluateSteps(List<Clazz> clazzes) {
        return criteria.getSteps().stream()
                .map(step -> evaluateStep(step, clazzes))
                .collect(Collectors.toList());
    }

    private StepResult evaluateStep(CriteriaStep step, List<Clazz> clazzes) {
        StepResult stepResult = new StepResult(step.getStep());
        step.getClazzes().forEach(criteriaClazz -> stepResult.addClazzResult(evaluateClazz(criteriaClazz, clazzes)));
        return stepResult;
    }

    private ClazzResult evaluateClazz(CriteriaClazz criteriaClazz, List<Clazz> clazzes) {
        ClazzResult clazzResult = new ClazzResult(criteriaClazz.getClazzName());
        findClazz(criteriaClazz.getClazzName(), clazzes).ifPresentOrElse(clazz -> {
            clazzResult.setClazzExists(true);
            clazzResult.setConstructorResults(Arrays.stream(criteriaClazz.getConstructors())
                    .map(constructor -> evaluateConstructor(constructor, clazz))
                    .toArray(ConstructorResult[]::new));

            clazzResult.setFieldResults(Arrays.stream(criteriaClazz.getFields())
                    .map(field -> evaluateField(field, clazz))
                    .toArray(FieldResult[]::new));

            clazzResult.setMethodResults(Arrays.stream(criteriaClazz.getMethods())
                    .map(method -> evaluateMethod(method, clazz))
                    .toArray(MethodResult[]::new));
        }, () -> clazzResult.setClazzExists(false));

        return clazzResult;
    }

    private ConstructorResult evaluateConstructor(CriteriaConstructor criteriaConstructor, Clazz clazz) {
        boolean[] results = clazz.hasConstructor(
                criteriaConstructor.getParameterTypes(),
                criteriaConstructor.getParameterNames(),
                criteriaConstructor.getVisibility()
        );
        return new ConstructorResult(results);
    }

    private FieldResult evaluateField(CriteriaField criteriaField, Clazz clazz) {
        boolean[] results = clazz.hasField(
                criteriaField.getName(),
                criteriaField.getType(),
                criteria.getGeneralCriteria().isAllAttributesPrivate(),
                criteriaField.getIsFinal()
        );
        return new FieldResult(criteriaField.getName(), results);
    }

    private MethodResult evaluateMethod(CriteriaMethod criteriaMethod, Clazz clazz) {
        boolean[] results = clazz.hasMethod(
                criteriaMethod.getName(),
                criteriaMethod.getParameterTypes(),
                criteriaMethod.getReturnType(),
                criteriaMethod.getVisibility()
        );
        return new MethodResult(criteriaMethod.getName(), results);
    }

    private Optional<Clazz> findClazz(String clazzName, List<Clazz> clazzes) {
        return clazzes.stream()
                .filter(clazz -> clazz.getClazzName().equals(clazzName))
                .findFirst();
    }
}
