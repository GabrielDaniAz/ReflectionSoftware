package com.javacorrige.controller;

import java.util.List;

import com.javacorrige.model.Student;
import com.javacorrige.service.pdf.PdfService;

import java.io.File;

public class PdfController {

    private List<Student> students;
    private File pdfDirectory;

    public PdfController(List<Student> students, File pdfDirectory) {
        this.students = students;
        this.pdfDirectory = pdfDirectory;
    }

    public void start() {
        for (Student student : students) {
            String studentName = student.getName();

            String pdfFileString = studentName + "_relatorio.pdf";
            File pdfFile = new File(pdfDirectory, pdfFileString);

            PdfService.generateCorrectionReport(student, pdfFile);
        }
    }
}

