package com.reflectionsoftware.service.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileService {

    // --- Métodos de Manipulação de Diretório ---

    public static void createDirectory(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static void removeDirectory(File directory) {
        if (directory.exists()) {
            deleteDirectory(directory);
        }
    }

    public static void resetDirectory(File directory) {
        removeDirectory(directory);
        createDirectory(directory);
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

    public static void organizeStepDirectories(File baseDirectory, String stepCorrection) {
        validateDirectoryPath(baseDirectory);
    
        // Lista as pastas em ordem alfanumérica
        File[] subDirectories = baseDirectory.listFiles(File::isDirectory);
        if (subDirectories == null || subDirectories.length == 0) {
            return; // Não há subpastas para organizar
        }
    
        Arrays.sort(subDirectories, (dir1, dir2) -> dir1.getName().compareToIgnoreCase(dir2.getName()));
    
        boolean found = false;
    
        // Mantém apenas as pastas até encontrar o `stepCorrection`
        for (File subDirectory : subDirectories) {
            if (found) {
                deleteDirectory(subDirectory); // Remove pastas após encontrar o stepCorrection
            } else if (subDirectory.getName().equalsIgnoreCase(stepCorrection)) {
                found = true;
            }
        }
    
        if (!found) {
            throw new IllegalArgumentException("A subpasta especificada como 'stepCorrection' não foi encontrada: " + stepCorrection);
        }
    }


    // --- Métodos de Manipulação de Arquivo ---

    public static void writeFile(File file, String content) {
        try {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            // Grava o conteúdo no arquivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar arquivo: " + file.getAbsolutePath());
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

    public static List<File> getAllJarFiles(File directory) {
        List<File> jarFiles = new ArrayList<>();
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory() && file.getName().equals("bin")) continue;
                    if (file.isDirectory()) {
                        jarFiles.addAll(getAllJarFiles(file));
                    } else if (file.isFile() && file.getName().endsWith(".jar")) {
                        jarFiles.add(file);
                    }
                }
            }
        }
        return jarFiles;
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
