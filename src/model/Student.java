package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um aluno com informações como nome, diretório raiz, arquivos submetidos, 
 * resultados de reflexão e resultados de compilação.
 */
public class Student {

    private String name;
    private File rootDirectory;
    private List<File> submittedFiles;
    private List<String> reflectionResults;
    private CompilationResult compilationResult;

    /**
     * Construtor da classe Student.
     * 
     * @param rootDirectory Diretório raiz do aluno.
     * @param submittedFiles Lista de arquivos Java submetidos pelo aluno.
     */
    public Student(File rootDirectory, List<File> submittedFiles) {
        this.rootDirectory = rootDirectory;
        this.submittedFiles = submittedFiles;
        this.name = rootDirectory.getName();  // O nome do aluno é derivado do nome da pasta
        this.reflectionResults = new ArrayList<>();
    }

    /**
     * Retorna o nome do aluno.
     * 
     * @return O nome do aluno.
     */
    public String getName() {
        return name;
    }

    /**
     * Define um novo nome para o aluno.
     * 
     * @param name O novo nome do aluno.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna a lista de arquivos submetidos pelo aluno.
     * 
     * @return Lista de arquivos submetidos {@link File}.
     */
    public List<File> getSubmittedFiles() {
        return submittedFiles;
    }

    /**
     * Define a lista de arquivos submetidos pelo aluno.
     * 
     * @param submittedFiles A nova lista de arquivos submetidos.
     */
    public void setSubmittedFiles(List<File> submittedFiles) {
        this.submittedFiles = submittedFiles;
    }

    /**
     * Retorna a lista de resultados de reflexão obtidos após a análise das classes.
     * 
     * @return Lista de resultados da reflexão {@link String}.
     */
    public List<String> getReflectionResults() {
        return reflectionResults;
    }

    /**
     * Define a lista de resultados de reflexão após a análise.
     * 
     * @param reflectionResults A nova lista de resultados de reflexão.
     */
    public void setReflectionResults(List<String> reflectionResults) {
        this.reflectionResults = reflectionResults;
    }

    /**
     * Retorna o resultado da compilação dos arquivos submetidos pelo aluno.
     * 
     * @return Resultado da compilação {@link CompilationResult}.
     */
    public CompilationResult getCompilationResult() {
        return compilationResult;
    }

    /**
     * Define o resultado da compilação para os arquivos submetidos pelo aluno.
     * 
     * @param compilationResult O novo resultado de compilação.
     */
    public void setCompilationResult(CompilationResult compilationResult) {
        this.compilationResult = compilationResult;
    }

    /**
     * Adiciona um novo arquivo à lista de arquivos submetidos pelo aluno.
     * 
     * @param file Arquivo a ser adicionado.
     */
    public void addSubmittedFile(File file) {
        this.submittedFiles.add(file);
    }

    /**
     * Retorna o diretório raiz onde os arquivos do aluno estão localizados.
     * 
     * @return O diretório raiz {@link File}.
     */
    public File getRootDirectory() {
        return rootDirectory;
    }
}
