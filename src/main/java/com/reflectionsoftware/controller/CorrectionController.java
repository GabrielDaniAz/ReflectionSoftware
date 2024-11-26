package com.reflectionsoftware.controller;

import java.util.List;

import com.reflectionsoftware.model.template.Template;
import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.service.reflection.CorrectionService;

public class CorrectionController {
    
    private Template template;
    private List<Student> students;
    private CorrectionService correctionService;

    public CorrectionController(Template template, List<Student> students, CorrectionService correctionService){
        this.template = template;
        this.students = students;
        this.correctionService = correctionService;
    }

    public void start(){
        for (Student student : students) {
            student.setReflectionResult(correctionService.correct(template, student));
        }
    }
}
