package com.reflectionsoftware.model.result.reflectionResult;

import java.util.List;
import java.util.stream.Collectors;

import com.reflectionsoftware.model.result.reflectionResult.step.StepResult;

public class ReflectionResult {

    private final String studentName;
    private final List<StepResult> stepResults;


    public ReflectionResult(String studentName, List<StepResult> stepResults) {
        this.studentName = studentName;
        this.stepResults = stepResults;
    }

    public List<StepResult> getResults() {
        return stepResults;
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public String toString() {
        return "ReflectionResult {" +
                "\n  studentName='" + studentName + '\'' +
                ",\n  stepResults=" + stepResults.stream()
                    .map(StepResult::toString)
                    .collect(Collectors.joining(", ")) +
                "\n}";
    }
}
