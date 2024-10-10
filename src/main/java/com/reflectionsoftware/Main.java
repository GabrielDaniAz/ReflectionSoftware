package com.reflectionsoftware;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.reflectionsoftware.controller.AppController;

public class Main {
    private static AppController app;
    // "C:/Users/gabri/Dev/ProvasAlunos"

    public static void main(String[] args) throws Exception {
        // Verifica se foi passado um argumento de diretório via linha de comando
        if (args.length < 1) {
            throw new IllegalArgumentException("O diretório de correção e o JSON devem ser fornecidos como argumentos.");
        }

        // Instalar a biblioteca Gson
        String rootDirectory = args[0];  // Usa o primeiro argumento como diretório
        String jsonFilePath = args[1];

        // Criar uma instância do Gson
        Gson gson = new Gson();

        try (BufferedReader br = new BufferedReader(new FileReader(jsonFilePath))) {
            // Ler o conteúdo do arquivo JSON
            Object json = gson.fromJson(br, Object.class); // Lê como um objeto genérico
            System.out.println(gson.toJson(json)); // Imprime o JSON lido
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            System.out.println("Erro de sintaxe no JSON: " + e.getMessage());
        }

        // JsonObject jsonObject = new JsonObject(jsonConfig);

        // Inicializa o AppController com o diretório
        app = new AppController(rootDirectory);
        app.start();
    }
}
