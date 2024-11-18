package com.reflectionsoftware.controller;

import java.util.List;
import java.nio.file.Paths;

import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.service.file.FileService;
import com.reflectionsoftware.service.pdf.PdfService;

public class PdfController {

    private List<Student> students;
    private String outputPdfFilePath;

    public PdfController(List<Student> students, String outputPdfFilePath) {
        if (students == null || students.isEmpty()) {
            throw new IllegalArgumentException("A lista de alunos não pode ser nula ou vazia.");
        }
        if (outputPdfFilePath == null || outputPdfFilePath.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo de saída não pode ser nulo ou vazio.");
        }

        this.students = students;
        this.outputPdfFilePath = outputPdfFilePath;
    }

    public void start() {
        for (Student student : students) {
            String studentName = student.getName();
            String outputFilePath = Paths.get(outputPdfFilePath, studentName + "_relatorio.pdf").toString();
            FileService.createDirectoryIfNotExists(Paths.get(outputFilePath).getParent().toString());

            try {
                PdfService.generateCorrectionReport(studentName, student.getReflectionResult(), outputFilePath);
            } catch (Exception e) {
                System.err.println("Erro ao gerar o relatório para o aluno " + studentName + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

