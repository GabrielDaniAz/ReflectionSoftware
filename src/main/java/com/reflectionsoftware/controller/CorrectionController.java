package com.reflectionsoftware.controller;

import java.util.List;

import com.reflectionsoftware.model.template.Template;
import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.model.result.correction.ReflectionResult;

public class CorrectionController {
    
    private Template template;
    private List<Student> students;

    public CorrectionController(Template template, List<Student> students){
        this.template = template;
        this.students = students;
    }

    public void start(){
        for (Student student : students) {
            if(!student.getCompilationResult().isSuccess()){
                continue;
            }
            student.setReflectionResult(new ReflectionResult(template, student.getClasses()));
        }
    }
}
