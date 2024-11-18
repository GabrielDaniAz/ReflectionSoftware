package com.reflectionsoftware.controller;

import java.util.List;

import com.reflectionsoftware.model.Professor;
import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.service.reflection.CorrectionService;

public class CorrectionController {
    
    private CorrectionService correctionService;
    private Professor professor;
    private List<Student> students;

    public CorrectionController(Professor professor, List<Student> students, CorrectionService correctionService){
        this.professor = professor;
        this.students = students;
        this.correctionService = correctionService;
    }

    public void start(){
        for (Student student : students) {
            student.setReflectionResult(correctionService.correct(professor.getTemplates(), student.getClasses()));
        }
    }
}
