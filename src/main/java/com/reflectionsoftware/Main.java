package com.reflectionsoftware;

import com.reflectionsoftware.controller.AppController;
import com.reflectionsoftware.util.validator.InputValidator;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            // Validar os argumentos de entrada
            InputValidator.validateArguments(args);

            // Converter as strings para objetos File
            File templateDirectory = new File(args[0]);
            File studentsDirectory = new File(args[1]);
            File pdfDirectory = new File(args[2]);
            String stepCorrection = args[3];

            // Criar e iniciar o controlador principal
            AppController app = new AppController(templateDirectory, studentsDirectory, pdfDirectory, stepCorrection);
            app.start();

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1); // Indica erro de execução
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
            System.exit(2); // Indica erro inesperado
        }
    }
}
