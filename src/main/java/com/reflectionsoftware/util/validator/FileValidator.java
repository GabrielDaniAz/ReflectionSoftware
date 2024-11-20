package com.reflectionsoftware.util.validator;

import java.io.File;

public class FileValidator {

    // Valida se o arquivo é um JSON
    public static boolean isJsonFile(File file) {
        return file.isFile() && file.getName().endsWith(".json");
    }
}
