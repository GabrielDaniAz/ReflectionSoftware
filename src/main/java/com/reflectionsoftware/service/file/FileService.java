package com.reflectionsoftware.service.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileService {

    // --- Métodos de Manipulação de Diretório ---

    public static void createDirectoryIfNotExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static void removeDirectoryIfExists(String path) {
        File directory = new File(path);
        if (directory.exists()) {
            deleteDirectory(directory);
        }
    }

    private static boolean deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return directory.delete();
    }

    // --- Métodos de Manipulação de Arquivo ---

    public static void writeFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Métodos de Busca de Arquivos Java ---

    public static Map<File, List<File>> getJavaFilesFromSubdirectories(String rootDirectory) {
        File rootDir = new File(rootDirectory);
        validateDirectoryPath(rootDir);

        Map<File, List<File>> javaFilesMap = new HashMap<>();
        List<File> subdirectories = retrieveSubdirectories(rootDir);
        for (File directory : subdirectories) {
            List<File> javaFiles = getAllJavaFiles(directory);
            javaFilesMap.put(directory, javaFiles);
        }

        return javaFilesMap;
    }

    public static List<File> getAllJavaFiles(File directory) {
        List<File> javaFiles = new ArrayList<>();
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory() && file.getName().equals("bin")) continue;
                    if (file.isDirectory()) {
                        javaFiles.addAll(getAllJavaFiles(file));
                    } else if (file.isFile() && file.getName().endsWith(".java")) {
                        javaFiles.add(file);
                    }
                }
            }
        }
        return javaFiles;
    }

    private static void validateDirectoryPath(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("O caminho fornecido não é um diretório válido.");
        }
    }

    public static List<File> retrieveSubdirectories(File rootDirectory) {
        validateDirectoryPath(rootDirectory);
        List<File> subdirectoryList = new ArrayList<>();
        File[] listFiles = rootDirectory.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    subdirectoryList.add(file);
                }
            }
        }
        return subdirectoryList;
    }
}
