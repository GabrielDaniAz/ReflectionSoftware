package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Gerencia a leitura e organização dos arquivos dentro da pasta de alunos. Identifica e carrega os arquivos .java.
public class FileManager {
    
    /**
     * Busca todos os arquivos .java em um diretório e suas subpastas.
     * 
     * @param directory Diretório raiz onde a busca será iniciada
     * @return Lista de arquivos .java encontrados.
     */

    public static List<File> getAllJavaFiles(File directory){
        List<File> javaFiles = new ArrayList<>();
        findJavaFiles(directory, javaFiles);
        return javaFiles;
    }

    /**
     * Método recursivo que percorre diretórios e coleta arquivos .java.
     * 
     * @param directory Diretório atual sendo analisado.
     * @param javaFiles Lista acumulativa dos arquivos .java encontrados.
     */
    private static void findJavaFiles(File directory, List<File> javaFiles){
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
                        findJavaFiles(file, javaFiles);
                    } else if(file.isFile() && file.getName().endsWith(".java")){
                        // Adiciona o arquivo .java encontrado à lista
                        javaFiles.add(file);
                    }
                }
            }
        }
    }

}
