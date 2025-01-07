package com.javacorrige.util.validator;

import java.io.File;

public class FileValidator {

    public static boolean fileExists(File file) {
        return file != null && file.exists();
    }

    public static boolean isDirectory(File directory) {
        return directory != null && directory.exists() && directory.isDirectory();
    }

    public static boolean hasExtension(File file, String extension) {
        return fileExists(file) && file.getName().toLowerCase().endsWith(extension.toLowerCase());
    }

    public static boolean isDirectoryEmpty(File directory) {
        File[] files = directory.listFiles();
        return files == null || files.length == 0;
    }

    public static boolean hasCompressedFile(File directory) {
        if (!isDirectory(directory)) return false;

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && (hasExtension(file, ".zip") || hasExtension(file, ".gz"))) {
                    return true;
                }
                else if (file.isDirectory() && hasCompressedFile(file)) {
                    return true;
                }
            }
        }

        return false;
    }
}
