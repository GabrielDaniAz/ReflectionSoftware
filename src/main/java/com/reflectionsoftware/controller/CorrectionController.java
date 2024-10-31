package com.reflectionsoftware.controller;

import java.io.File;
import java.util.List;

import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.model.result.Result;
import com.reflectionsoftware.model.result.compilation.CompilationResult;
import com.reflectionsoftware.model.result.reflectionResult.ReflectionResult;
import com.reflectionsoftware.service.CompilationService;
import com.reflectionsoftware.service.ReflectionService;

public class CorrectionController {

    private final List<Student> students;
    private final CompilationService compilationService;
    private final ReflectionService reflectionService;

    public CorrectionController(List<Student> students, CompilationService compilationService, ReflectionService reflectionService) {
        this.students = students;
        this.compilationService = compilationService;
        this.reflectionService = reflectionService;
        startCorrection();
    }

    public void startCorrection() {
        for (Student student : students) {
            correctStudent(student);
        }
    }

    private void correctStudent(Student student) {
        try {
            File binDirectory = new File(student.getRootDirectory(), "bin");

            CompilationResult compilationResult = compilationService.compile(student.getSubmittedFiles(), binDirectory);
            ReflectionResult reflectionResult = null;

            if (compilationResult.isSuccess()) {
                reflectionResult = reflectionService.analyzeReflection(compilationResult.getCompiledFiles(), binDirectory, student.getName());
            }

            student.setResult(new Result(compilationResult, reflectionResult));
            System.out.println(reflectionResult);
        } catch (Exception e) {
            System.err.println("Erro ao corrigir o aluno " + student.getName() + ": " + e.getMessage());
        }
    }
}
