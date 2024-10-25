package com.reflectionsoftware.controller;

import java.io.File;
import java.io.IOException;
import com.google.gson.JsonParseException;
import com.reflectionsoftware.model.criteria.CriteriaCorrection;
import com.reflectionsoftware.service.JsonConverterService;

/**
 * A classe {@code CriteriaController} é responsável por controlar o processo de
 * leitura e gerenciamento dos critérios de correção a partir de um arquivo JSON.
 * Ela utiliza o serviço {@link JsonConverterService} para converter o arquivo JSON
 * em um objeto {@link CriteriaCorrection} e define o passo de correção.
 */
public class CriteriaManager {
    
    private static CriteriaCorrection criteriaCorrection;

    /**
     * Constrói uma instância de {@code CriteriaController} e carrega os critérios de
     * correção a partir de um arquivo JSON. O passo de correção também é definido.
     *
     * @param jsonFilePath o caminho para o arquivo JSON que contém os critérios de correção
     * @param correctionStep o passo de correção a ser utilizado
     * @throws IOException se houver um erro ao ler o arquivo JSON
     * @throws IllegalArgumentException se o arquivo JSON não existir ou for inválido
     */
    public CriteriaManager(String jsonFilePath, String correctionStep) throws IOException {
        // Valida se o arquivo existe e se é um JSON válido
        validateJsonFile(jsonFilePath);
        // Define o passo de correção após a conversão do JSON
        criteriaCorrection.setCorrectionStep(correctionStep);
    }

    /**
     * Retorna o objeto {@code criteriaCorrection} carregado. Se o objeto ainda não foi
     * carregado, retorna uma nova instância de {@link CriteriaCorrection}.
     *
     * @return o objeto {@link CriteriaCorrection}, ou uma nova instância se o atual for nulo
     */
    public static CriteriaCorrection getCriteriaCorrection() {
        // Garante que um objeto criteriaCorrection válido seja retornado
        return (criteriaCorrection != null) ? criteriaCorrection : new CriteriaCorrection();
    }

    /**
     * Valida o arquivo JSON, verificando se existe e se é um JSON válido.
     *
     * @param jsonFilePath o caminho para o arquivo JSON
     * @throws IOException se houver erro de leitura do arquivo
     * @throws IllegalArgumentException se o arquivo não existir ou for inválido
     */
    private void validateJsonFile(String jsonFilePath) throws IOException {
        validateJsonFileExists(jsonFilePath);
        validateJsonFormat(jsonFilePath);
    }

    /**
     * Valida se o arquivo JSON existe no caminho especificado.
     *
     * @param jsonFilePath o caminho para o arquivo JSON
     * @throws IllegalArgumentException se o arquivo não existir
     */
    private void validateJsonFileExists(String jsonFilePath) {
        File file = new File(jsonFilePath);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("O arquivo JSON especificado não existe: " + jsonFilePath);
        }
    }

    /**
     * Valida se o arquivo é um JSON válido.
     *
     * @param jsonFilePath o caminho para o arquivo JSON
     * @throws IOException se houver um erro de leitura do arquivo
     * @throws JsonParseException se o arquivo não for um JSON válido
     */
    private void validateJsonFormat(String jsonFilePath) throws IOException {
        JsonConverterService jsonConverterService = new JsonConverterService();
        try {
            // Tenta converter o JSON para verificar se está no formato correto
            criteriaCorrection = jsonConverterService.convertJsonToObject(jsonFilePath);
        } catch (JsonParseException e) {
            throw new IllegalArgumentException("O arquivo não é um JSON válido: " + jsonFilePath, e);
        }
    }
}