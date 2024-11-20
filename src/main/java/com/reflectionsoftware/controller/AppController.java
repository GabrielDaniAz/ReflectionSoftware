package com.reflectionsoftware.controller;

import java.io.File;

import com.reflectionsoftware.manager.StudentManager;
import com.reflectionsoftware.model.template.Template;
import com.reflectionsoftware.service.converter.JsonConverter;
import com.reflectionsoftware.service.file.FileService;
import com.reflectionsoftware.service.reflection.CorrectionService;
import com.reflectionsoftware.util.processor.ClassProcessor;
import com.reflectionsoftware.util.processor.IClassProcessor;
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

        IClassProcessor classProcessor = new ClassProcessor();
        Template template = new Template(classProcessor.processDirectoryToClasses(templateDirectory));
        StudentManager studentManager = new StudentManager(classProcessor.processDirectoryToClasses(studentsDirectory));

        template.getExercises().stream()
        .flatMap(e -> e.getClasses().stream()) // Itera pelas classes de cada exercício
        .filter(c -> c.getCompilationResult() != null) // Filtra classes com resultado de compilação não nulo
        .forEach(c -> System.out.println(c.getCompilationResult().getDiagnostics())); // Imprime os diagnósticos


        CorrectionService correctionService = new CorrectionService();
        CorrectionController correctionController = new CorrectionController(template, studentManager.getStudents(), correctionService);
        correctionController.start();

        PdfController pdfController = new PdfController(studentManager.getStudents(), pdfDirectory);
        pdfController.start();
    }
}
