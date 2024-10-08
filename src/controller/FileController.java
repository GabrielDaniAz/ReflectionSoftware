package controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * FileController gerencia a coleta de arquivos .java dos subdiretórios de um diretório raiz.
 */
public class FileController {

    private final File rootDirectory;
    private final List<File> subdirectories;
    private final Map<File, List<File>> javaFilesFromDirectory;

    /**
     * Construtor para inicializar o controlador com o diretório raiz e processar os arquivos .java.
     * 
     * @param rootDirectory O diretório raiz onde os subdiretórios serão pesquisados.
     */
    public FileController(String rootDirectory) {
        validateDirectoryPath(rootDirectory);
        this.rootDirectory = new File(rootDirectory);
        this.subdirectories = retrieveSubdirectories();
        this.javaFilesFromDirectory = initializeJavaFiles();
    }

    /**
     * Inicializa o mapa de subdiretórios e seus arquivos .java correspondentes.
     * 
     * @param rootDirectory O diretório raiz.
     * @return Mapa com o nome do subdiretório e a lista de arquivos .java.
     */
    private Map<File, List<File>> initializeJavaFiles() {
        Map<File, List<File>> javaFilesMap = new HashMap<>();

        for (File directory : subdirectories) {
            List<File> javaFiles = getAllJavaFiles(directory);
            javaFilesMap.put(directory, javaFiles);
        }

        return javaFilesMap;
    }

    /**
     * Busca todos os arquivos .java em um diretório e suas subpastas.
     * 
     * @param directory Diretório raiz onde a busca será iniciada
     * @return Lista de arquivos .java encontrados.
     */
    private static List<File> getAllJavaFiles(File directory){
        List<File> javaFiles = new ArrayList<>();
        // Verifica se o diretório existe e é realmente uma pasta
        if(directory.exists() && directory.isDirectory()){
            // Lista todos os arquivos e subpastas dentro de diretório
            File[] files = directory.listFiles();
            if(files != null) {
                for (File file : files) {
                    // Ignora a pasta bin
                    if(file.isDirectory() && file.getName().equals("bin")) continue;
                    
                    if(file.isDirectory()){
                        // Recursão para subpastas
                        javaFiles.addAll(getAllJavaFiles(file));
                    } else if(file.isFile() && file.getName().endsWith(".java")){
                        // Adiciona o arquivo .java encontrado à lista
                        javaFiles.add(file);
                    }
                }
            }
        }
        return javaFiles;
    }

    /**
     * Valida se o caminho fornecido é um diretório existente e válido.
     * 
     * @param directoryPath O caminho do diretório a ser validado.
     * @throws IllegalArgumentException Se o caminho fornecido não for um diretório válido.
     */
    private void validateDirectoryPath(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("O caminho fornecido não é um diretório válido.");
        }
    }


    /**
     * Retorna uma lista de subdiretórios dentro do diretório raiz.
     * 
     * @return Lista de subdiretórios encontrados.
     */
    private List<File> retrieveSubdirectories() {
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

    /**
     * Retorna o mapa contendo o nome do subdiretório e os arquivos .java correspondentes.
     * 
     * @return Mapa de subdiretórios e arquivos .java.
     */
    public Map<File, List<File>> getJavaFilesFromDirectory() {
        return javaFilesFromDirectory;
    }
}
