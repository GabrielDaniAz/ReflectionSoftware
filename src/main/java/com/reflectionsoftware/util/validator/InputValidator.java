package com.reflectionsoftware.util.validator;

import java.io.File;
import java.io.IOException;

import com.reflectionsoftware.service.file.FileService;

public class InputValidator {

    public static void validateArguments(String[] args) {
        if (args.length < 4) {
            throw new IllegalArgumentException("Devem ser passados pelo menos 4 argumentos: [Gabarito (JSON/Diretório), Diretório dos Exercícios, Diretório dos PDFs, Etapa de Correção].");
        }

        // Validar Gabarito (JSON ou Diretório)
        File templateFileOrDirectory = new File(args[0]);
        validateTemplate(templateFileOrDirectory);

        // Validar Diretório dos Exercícios dos Alunos
        File studentsDirectory = new File(args[1]);
        validateStudentsDirectory(studentsDirectory);

        // Validar Diretório de PDFs (criar se não existir)
        File pdfDirectory = new File(args[2]);
        FileService.createDirectory(pdfDirectory);

        // Validar Etapa de Correção
        String stepCorrection = args[3];
        validateCorrectionStep(templateFileOrDirectory, stepCorrection);
    }

    private static void validateTemplate(File fileOrDirectory) {
        if (!fileOrDirectory.exists()) {
            throw new IllegalArgumentException("O gabarito fornecido não existe: " + fileOrDirectory.getPath());
        }

        if (fileOrDirectory.isFile() && !fileOrDirectory.getName().endsWith(".json")) {
            throw new IllegalArgumentException("Se o gabarito for um arquivo, ele deve ser um JSON válido: " + fileOrDirectory.getPath());
        }

        if (!fileOrDirectory.isDirectory() && !fileOrDirectory.isFile()) {
            throw new IllegalArgumentException("O gabarito deve ser um JSON ou um diretório válido.");
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

    private static void validateCorrectionStep(File templateFileOrDirectory, String step) {
        if (templateFileOrDirectory.isFile()) {
            // Verificar se o JSON contém a etapa de correção
            if (!isStepInJson(templateFileOrDirectory, step)) {
                throw new IllegalArgumentException("A etapa de correção '" + step + "' não foi encontrada no arquivo JSON: " + templateFileOrDirectory.getPath());
            }
        } else if (templateFileOrDirectory.isDirectory()) {
            // Verificar se o diretório contém uma subpasta correspondente à etapa
            File stepDirectory = new File(templateFileOrDirectory, step);
            if (!stepDirectory.exists() || !stepDirectory.isDirectory()) {
                throw new IllegalArgumentException("A etapa de correção '" + step + "' não foi encontrada como subpasta em: " + templateFileOrDirectory.getPath());
            }
        } else {
            throw new IllegalArgumentException("O caminho do gabarito não é válido para validação da etapa de correção.");
        }
    }

    private static boolean isStepInJson(File jsonFile, String step) {
        try {
            return JsonValidator.doesStepExist(jsonFile, step);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar o arquivo JSON: " + jsonFile.getPath(), e);
        }
    }
    
}
