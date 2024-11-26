package com.reflectionsoftware.controller;

import java.io.File;
import java.util.List;

import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.model.template.Template;
import com.reflectionsoftware.service.converter.JsonConverter;
import com.reflectionsoftware.service.file.FileService;
import com.reflectionsoftware.service.reflection.CorrectionService;
import com.reflectionsoftware.util.processor.StudentProcessor;
import com.reflectionsoftware.util.processor.TemplateProcessor;
import com.reflectionsoftware.util.validator.FileValidator;

public class AppController {
    private File templateDirectory;
    private File studentsDirectory;
    private File pdfDirectory;
    private String stepCorrection;

    private static final String TEMPLATE_DIR = "template";

    public AppController(File templateDirectory, File studentsDirectory, File pdfDirectory, String stepCorrection) {
        this.templateDirectory = templateDirectory;   
        this.studentsDirectory = studentsDirectory;
        this.pdfDirectory = pdfDirectory;
        this.stepCorrection = stepCorrection;
    }
    
    public void start() {

        if(FileValidator.isJsonFile(templateDirectory)){
            File actualDirectory = new File(System.getProperty("user.dir") + File.separator + TEMPLATE_DIR);
            JsonConverter.convertJsonToJava(templateDirectory, actualDirectory);
            templateDirectory = actualDirectory;
        }

        FileService.organizeStepDirectories(templateDirectory, stepCorrection);

        Template template = TemplateProcessor.processTemplateDirectory(templateDirectory);
        List<Student> students = StudentProcessor.processStudentDirectory(studentsDirectory);

        CorrectionService correctionService = new CorrectionService();
        CorrectionController correctionController = new CorrectionController(template, students, correctionService);
        correctionController.start();

        PdfController pdfController = new PdfController(students, pdfDirectory);
        pdfController.start();
    }
}
