package com.reflectionsoftware;

import com.reflectionsoftware.controller.AppController;

public class Main {
    private static AppController app;

    public static void main(String[] args) throws Exception {
        // Verifica se foi passado um argumento de diretório via linha de comando
        if (args.length < 4) {
            throw new IllegalArgumentException("O diretório de correção, o JSON e o caminho do PDF de saída devem ser fornecidos como argumentos.");
        }

        String rootDirectory = args[0];  // Usa o primeiro argumento como diretório
        String jsonFilePath = args[1];   // Usa o segundo argumento como caminho do JSON
        String outputPdfPath = args[2];  // Usa o terceiro argumento como caminho do PDF de saída
        int correctionStep = Integer.parseInt(args[3]); // Usa o quarto argumento como o passo limite para a correção

        // Inicializa o AppController com o diretório
        app = new AppController(rootDirectory, jsonFilePath, outputPdfPath, correctionStep);
        app.start();
    }
}
