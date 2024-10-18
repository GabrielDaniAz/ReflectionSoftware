package com.reflectionsoftware.model;

import java.io.File;
import java.util.List;

import com.reflectionsoftware.model.result.Result;

/**
 * Representa um aluno com informações como nome, diretório raiz, arquivos submetidos, 
 * resultados de reflexão e resultados de compilação.
 */
public class Student {

    private String name;
    private File rootDirectory;
    private List<File> submittedFiles;
    private Result result;

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
     * Retorna resultado obtidos após a compilação e análise das classes.
     * 
     * @return Resultados da correção.
     */
    public Result getResult() {
        return result;
    }

    /**
     * Define a lista de resultados de reflexão após a análise.
     * 
     * @param reflectionResults A nova lista de resultados de reflexão.
     */
    public void setResult(Result result) {
        this.result = result;
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
