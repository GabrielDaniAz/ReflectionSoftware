package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.CompilationResult;

// Responsável por compilar os arquivos Java dos alunos utilizando ProcessBuilder e lidar com os erros de compilação.
public class CompilationService {
    
    /**
     * Compila um arquivo .java e retorna o resultado da compilação.
     * 
     * @param javaFile Arquivo .java a ser compilado.
     * @return Resultado da compilação encapsulado em um CompilationResult.
     */

     public static CompilationResult compileJavaFile(File javaFile) {
        List<String> messages = new ArrayList<>();
        boolean success = false;

        try {
            // Usa o ProcessBuilder para invocar o compilador 'javac'
            ProcessBuilder processBuilder = new ProcessBuilder("javac", javaFile.getAbsolutePath());

            // Define o diretório onde a compilação deve ser feita
            processBuilder.directory(javaFile.getParentFile());

            // Inicia o processo de compilação
            Process process = processBuilder.start();

            // Aguarda a conclusão do processo de compilação
            int exitCode = process.waitFor();

            success = (exitCode == 0);

            if(!success) {
                messages.add("Erro ao compilar: " + javaFile.getName());
            } 
        } catch (IOException | InterruptedException e) {
            messages.add("Exceção ao compilar: " + e.getMessage());
        }

        return new CompilationResult(javaFile, success, messages);
     }
}
