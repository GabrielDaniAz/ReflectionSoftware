package com.reflectionsoftware.util.validator;

import java.io.File;

import com.reflectionsoftware.service.file.FileService;

public class InputValidator {

    public static void validateArguments(String[] args) {
        if (args.length < 4) {
            throw new IllegalArgumentException(
                "Devem ser passados pelo menos 4 argumentos: [Diretório do Gabarito, Diretório dos Exercícios, Diretório dos PDFs, Etapa de Correção]."
            );
        }

        validateTemplateDirectory(new File(args[0]));
        validateStudentsDirectory(new File(args[1]));
        validatePdfDirectory(new File(args[2]));
        validateCorrectionStep(new File(args[0]), args[3]);
    }

    private static void validateTemplateDirectory(File directory) {
        if (!FileValidator.isDirectory(directory)) {
            throw new IllegalArgumentException("O diretório do gabarito não existe: " + directory.getPath());
        }
        if(FileValidator.isDirectoryEmpty(directory)){
            throw new IllegalStateException("O diretório do gabarito deve conter pelo menos uma subpasta.");
        }
    }

    private static void validateStudentsDirectory(File directory) {
        if (!FileValidator.isDirectory(directory)) {
            throw new IllegalArgumentException("O diretório dos exercícios dos alunos não existe: " + directory.getPath());
        }
    }

    private static void validatePdfDirectory(File directory) {
        if (!FileValidator.isDirectory(directory)) {
            if (!FileService.createDirectory(directory)) {
                throw new IllegalStateException("Falha ao criar o diretório para PDF: " + directory.getPath());
            }
        }
    }

    private static void validateCorrectionStep(File templateDirectory, String step) {
        File stepDirectory = new File(templateDirectory, step);

        if (!FileValidator.isDirectory(stepDirectory)) {
            throw new IllegalArgumentException(
                "A etapa de correção '" + step + "' não foi encontrada como subpasta em: " + templateDirectory.getPath()
            );
        }
    }
}
