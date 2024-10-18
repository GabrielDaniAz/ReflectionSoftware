package com.reflectionsoftware.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reflectionsoftware.model.criteria.CorrectionCriteria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe gerenciadora dos critérios de correção.
 * Carrega critérios a partir de um arquivo JSON e fornece acesso a eles.
 */
public class CorrectionCriteriaManager {

    private static CorrectionCriteria correctionCriteria; // Instância dos critérios de correção

    /**
     * Carrega os critérios de correção a partir de um arquivo JSON.
     *
     * @param jsonFilePath O caminho para o arquivo JSON que contém os critérios.
     */
    public static void fromJson(String jsonFilePath) {
        Gson gson = new GsonBuilder().create(); // Cria um objeto Gson para a conversão de JSON
        try (BufferedReader br = new BufferedReader(new FileReader(jsonFilePath))) {
            correctionCriteria = gson.fromJson(br, CorrectionCriteria.class); // Converte o JSON em um objeto CorrectionCriteria
        } catch (IOException e) {
            e.printStackTrace(); // Exibe a pilha de erro para depuração
            correctionCriteria = null; // Define como nulo se ocorrer um erro
        }
    }

    /**
     * Obtém a instância carregada de critérios de correção.
     *
     * @return A instância de CorrectionCriteria carregada.
     * @throws IllegalStateException se os critérios não foram carregados.
     */
    public static CorrectionCriteria getCorrectionCriteria() {
        if (correctionCriteria == null) {
            throw new IllegalStateException("Os critérios de correção não foram carregados.");
        }
        return correctionCriteria; // Retorna a instância carregada
    }
}
