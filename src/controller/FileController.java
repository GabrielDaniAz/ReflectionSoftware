package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.FileManager;

//Responsável por gerenciar a interação com o sistema de arquivos. Ele solicitará a pasta onde estão localizadas as provas dos alunos.
public class FileController {

    /**
     * Solicita a pasta onde estão localizadas as provas dos alunos e retorna um mapa associando
     * o nome do aluno (nome da pasta) aos arquivos .java encontrados.
     * 
     * @param directoryPath Caminho da pasta onde estão localizadas as pastas dos alunos. 
     * @return Mapa de alunos e seus arquivos .java. 
     * @throws IllegalArgumentException Se o caminho fornecido não for uma pasta válida. 
     */
    public static Map<String, List<File>> getJavaFilesByStudent(String directoryPath) throws IllegalArgumentException {
        File directory = new File(directoryPath);

        if(!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("O caminho fornecido não é uma pasta válida.");
        }

        // Busca todos os arquivos .java utilizando FileManager
        List<File> javaFiles = FileManager.getAllJavaFiles(directory);

        // Agrupa os arquivos .java por aluno (nome da pasta pai)
        return groupFilesByStudent(javaFiles, directory);
    }

    /**
     * Agrupa os arquivos .java por aluno, subindo a hierarquia de pastas até encontrar a pasta do aluno. 
     * 
     * @param javaFiles Lista de arquivos .java encontrados. 
     * @param rootDirectory Diretório raiz que contém as pastas dos alunos.
     * @return Mapa que associa o nome do aluno (pasta pai) à lista de arquivos .java. 
     */
    private static Map<String, List<File>> groupFilesByStudent(List<File> javaFiles, File rootDirectory) {
        Map<String, List<File>> studentFilesMap = new HashMap<>();

        for (File file : javaFiles) {
            String studentName = getStudentName(file, rootDirectory);
            studentFilesMap.computeIfAbsent(studentName, k -> new ArrayList<>()).add(file);
        }

        return studentFilesMap;
    }

    /**
     * Identifica o nome do aluno subindo na hierarquia de pastas até encontrar a pasta de
     * nível superior diretamente abaixo do diretório raiz (pastas com nome dos alunos)
     * 
     * @param file O arquivo .java
     * @param rootDirectory O diretório raiz que contém as pastas dos alunos
     * @return O nome da pasta que representa o aluno
     */
    private static String getStudentName(File file, File rootDirectory) {
        File parent = file.getParentFile();

        // Subimos na hierarquia de pastas até encontrar a pasta diretamente abaixo do root directory
        while(parent != null && !parent.getParentFile().equals(rootDirectory)) {
            parent = parent.getParentFile();
        }

        return parent != null ? parent.getName() : "Desconhecido";
    }
}

