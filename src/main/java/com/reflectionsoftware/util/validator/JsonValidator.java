package com.reflectionsoftware.util.validator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonValidator {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static boolean doesStepExist(File jsonFile, String step) throws IOException {
        JsonNode rootNode = objectMapper.readTree(jsonFile);
        JsonNode stepsNode = rootNode.get("passos");

        if (stepsNode == null || !stepsNode.isArray()) {
            throw new IllegalArgumentException("O JSON não contém o campo 'passos' ou ele não é uma lista.");
        }

        for (JsonNode stepNode : stepsNode) {
            if (stepNode.has("passo") && stepNode.get("passo").asText().equals(step)) {
                return true;
            }
        }

        return false;
    }

    public static Map<String, Object> convertJsonToMap(File jsonFile) {
        try {
            return objectMapper.readValue(jsonFile, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            // Captura exceções de I/O, como problemas ao ler o arquivo ou ao processar o JSON
            System.err.println("Erro ao processar o arquivo JSON: " + e.getMessage());
            e.printStackTrace();
            return null; 
        }
    }
    

    public static boolean isValidJson(File jsonFile) {
        try {
            objectMapper.readTree(jsonFile); // Tenta parsear o JSON
            return true;
        } catch (IOException e) {
            return false; // Caso o JSON seja inválido
        }
    }
}
