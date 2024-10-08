package model;

import java.io.File;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.List;
import java.util.ArrayList;

/**
 * Representa o resultado da compilação de arquivos Java, contendo informações
 * sobre o sucesso ou falha, arquivos compilados e diagnósticos gerados.
 */
public class CompilationResult {

    private File directory;
    private List<File> compiledFiles;
    private boolean success;
    private List<Diagnostic<? extends JavaFileObject>> diagnostics;

    /**
     * Construtor da classe CompilationResult.
     * 
     * @param directory O diretório onde os arquivos .class compilados estão localizados.
     * @param success Indica se a compilação foi bem-sucedida.
     * @param diagnostics Lista de diagnósticos gerados durante a compilação.
     */
    public CompilationResult(File directory, boolean success, List<Diagnostic<? extends JavaFileObject>> diagnostics) {
        this.directory = directory;
        this.success = success;
        this.diagnostics = diagnostics;
        this.compiledFiles = findClassFiles(directory);
    }

    /**
     * Retorna o diretório onde a compilação foi realizada.
     * 
     * @return O diretório de compilação.
     */
    public File getDirectory() {
        return directory;
    }

    /**
     * Verifica se a compilação foi bem-sucedida.
     * 
     * @return true se a compilação foi bem-sucedida, false caso contrário.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Retorna a lista de diagnósticos gerados durante a compilação.
     * 
     * @return Lista de diagnósticos {@link Diagnostic}.
     */
    public List<Diagnostic<? extends JavaFileObject>> getDiagnostics() {
        return diagnostics;
    }

    /**
     * Retorna a lista de arquivos .class gerados pela compilação.
     * 
     * @return Lista de arquivos compilados.
     */
    public List<File> getCompiledFiles() {
        return compiledFiles;
    }

    /**
     * Busca recursivamente por arquivos .class no diretório de compilação e subdiretórios.
     * 
     * @param directory Diretório atual sendo analisado.
     * @return Lista de arquivos .class encontrados.
     */
    private List<File> findClassFiles(File directory) {
        List<File> compiledFiles = new ArrayList<>(); // Lista para armazenar os arquivos encontrados

        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    // Recursivamente busca em subdiretórios
                    compiledFiles.addAll(findClassFiles(file));
                } else if (file.getName().endsWith(".class")) {
                    // Adiciona arquivos .class encontrados
                    compiledFiles.add(file);
                }
            }
        }
        return compiledFiles; // Retorna a lista de arquivos encontrados
    }
}
