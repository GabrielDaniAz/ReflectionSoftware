package com.reflectionsoftware.util.validator;

import java.io.File;

import com.reflectionsoftware.service.file.FileService;

public class InputValidator {

    public static void validateArguments(String[] args) {
        // Verificar se o número mínimo de argumentos foi passado
        if (args.length < 4) {
            throw new IllegalArgumentException("Devem ser passados pelo menos 4 argumentos: [Diretório do Gabarito, Diretório dos Exercícios, Diretório dos PDFs, Etapa de Correção].");
        }

        // Validar Gabarito (JSON ou Diretório)
        File templateDirectory = new File(args[0]);
        validateTemplate(templateDirectory);

        // Validar Diretório dos Exercícios dos Alunos
        File studentsDirectory = new File(args[1]);
        validateStudentsDirectory(studentsDirectory);

        // Validar Diretório de PDFs (criar se não existir)
        File pdfDirectory = new File(args[2]);
        FileService.createDirectory(pdfDirectory);

        // Validar Etapa de Correção
        String stepCorrection = args[3];
        validateCorrectionStep(templateDirectory, stepCorrection);
    }

    private static void validateTemplate(File directory) {
        if (!directory.exists()) {
            throw new IllegalArgumentException("O diretório do gabarito fornecido não existe: " + directory.getPath());
        }

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("O gabarito deve ser um diretório válido.");
        }
    }

    private static void validateStudentsDirectory(File directory) {
        if (!directory.exists()) {
            throw new IllegalArgumentException("O diretório dos exercícios dos alunos não existe: " + directory.getPath());
        }

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("O caminho fornecido para os exercícios deve ser um diretório: " + directory.getPath());
        }
    }

    private static void validateCorrectionStep(File templateDirectory, String step) {
        // Verificar se o diretório contém uma subpasta correspondente à etapa
        File stepDirectory = new File(templateDirectory, step);
        if (!stepDirectory.exists() || !stepDirectory.isDirectory()) {
            throw new IllegalArgumentException("A etapa de correção '" + step + "' não foi encontrada como subpasta em: " + templateDirectory.getPath());
        }
    }
}
