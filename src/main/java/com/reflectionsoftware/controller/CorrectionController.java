package com.reflectionsoftware.controller;

import java.util.List;

import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.service.reflection.CorrectionService;

public class CorrectionController {
    
    private CorrectionService correctionService;
    private List<Student> students;

    public CorrectionController(List<Student> students, CorrectionService correctionService){
        this.students = students;
        this.correctionService = correctionService;
    }

    public void start(){
        for (Student student : students) {
            List<Class<?>> classes = student.getCompilationResult().getExercise().getClasses();
            student.setReflectionResult(correctionService.correctClasses(classes));
        }
    }
}
