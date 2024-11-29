package com.reflectionsoftware.controller;

import java.io.File;
import java.util.List;

import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.model.template.Template;
import com.reflectionsoftware.service.file.FileService;
import com.reflectionsoftware.util.processor.StudentProcessor;
import com.reflectionsoftware.util.processor.TemplateProcessor;

public class AppController {
    private File templateDirectory;
    private File studentsDirectory;
    private File pdfDirectory;
    private String stepCorrection;

    public AppController(File templateDirectory, File studentsDirectory, File pdfDirectory, String stepCorrection) {
        this.templateDirectory = templateDirectory;   
        this.studentsDirectory = studentsDirectory;
        this.pdfDirectory = pdfDirectory;
        this.stepCorrection = stepCorrection;
    }
    
    public void start() {
        FileService.organizeStepDirectories(templateDirectory, stepCorrection);

        Template template = TemplateProcessor.processTemplateDirectory(templateDirectory);
        List<Student> students = StudentProcessor.processStudentDirectory(studentsDirectory);

        CorrectionController correctionController = new CorrectionController(template, students);
        correctionController.start();

        PdfController pdfController = new PdfController(students, pdfDirectory);
        pdfController.start();
    }
}
