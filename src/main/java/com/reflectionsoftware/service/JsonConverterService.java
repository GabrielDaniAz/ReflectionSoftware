package com.reflectionsoftware.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import com.reflectionsoftware.model.criteria.CriteriaCorrection;

public class JsonConverterService {

    private Gson gson;

    public JsonConverterService() {
        this.gson = new Gson();
    }

    // Método para converter JSON de um arquivo em um objeto Exercicio
    public CriteriaCorrection convertJsonToObject(String jsonFilePath) throws IOException {
        try (FileReader reader = new FileReader(jsonFilePath)) {
            // Definindo o tipo Exercicio
            Type type = new TypeToken<CriteriaCorrection>() {}.getType();
            return gson.fromJson(reader, type);
        }
    }
}
