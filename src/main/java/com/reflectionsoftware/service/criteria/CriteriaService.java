package com.reflectionsoftware.service.criteria;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.reflectionsoftware.model.criteria.Criteria;

public class CriteriaService {

    public static Criteria getCriteria(String jsonFilePath, int correctionStep) {
        try {
            validateJsonFile(jsonFilePath);
            Criteria criteria = JsonConverterService.convertJsonToObject(jsonFilePath);
            criteria.filterStepsUpTo(correctionStep);
            return criteria;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void validateJsonFile(String jsonFilePath) {
        File file = new File(jsonFilePath);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("O arquivo JSON especificado não existe: " + jsonFilePath);
        }
    }
}
