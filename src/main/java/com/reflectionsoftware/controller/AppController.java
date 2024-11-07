package com.reflectionsoftware.controller;

import java.util.List;

import com.reflectionsoftware.model.Professor;
import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.service.person.PersonService;
import com.reflectionsoftware.service.reflection.CorrectionService;

public class AppController {
    private String rootDirectory;
    private String jsonFilePath;
    private String outputPdfFilePath;
    private int untilStep;

    public AppController(String rootDirectory, String jsonFilePath, String outputPdfFilePath, int untilStep) {
        this.rootDirectory = rootDirectory;
        this.jsonFilePath = jsonFilePath;
        this.outputPdfFilePath = outputPdfFilePath;
        this.untilStep = untilStep;
    }
    
    public void start() throws Exception {

        Professor professor = PersonService.getProfessor(jsonFilePath, untilStep);
        List<Student> students = PersonService.getStudents(rootDirectory);

        CorrectionService correctionService = new CorrectionService(professor);
        CorrectionController correctionController = new CorrectionController(students, correctionService);
        correctionController.start();

        PdfController pdfController = new PdfController(students, outputPdfFilePath);
        pdfController.start();
    }
}
