package com.reflectionsoftware.service.criteria;

import com.google.gson.Gson;
import com.reflectionsoftware.model.criteria.Criteria;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonConverterService {

    private static final Gson GSON = new Gson();

    // Método para converter JSON de um arquivo em um objeto Criteria
    public static Criteria convertJsonToObject(String jsonFilePath) throws FileNotFoundException, IOException  {
        try (FileReader reader = new FileReader(jsonFilePath);
             BufferedReader br = new BufferedReader(reader)) {
            String jsonContent = preprocessJson(br);
            return GSON.fromJson(jsonContent, Criteria.class);
        }
    }

    private static String preprocessJson(BufferedReader br) throws IOException {
        StringBuilder jsonContent = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                jsonContent.append(line).append(System.lineSeparator());
            }
        }

        return jsonContent.toString();
    }
}