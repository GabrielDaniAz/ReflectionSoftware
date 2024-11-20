package com.reflectionsoftware.controller;

import java.util.List;
import java.io.File;

import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.service.file.FileService;
import com.reflectionsoftware.service.pdf.PdfService;

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
            FileService.createDirectory(pdfFile);

            try {
                PdfService.generateCorrectionReport(studentName, student.getReflectionResult(), pdfFile);
            } catch (Exception e) {
                System.err.println("Erro ao gerar o relatório para o aluno " + studentName + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

