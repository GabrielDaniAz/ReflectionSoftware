package model;

import java.io.File;
import java.util.List;

// Representa um aluno com informações como nome e uma lista de provas.
public class Student {
    private String name;
    private List<File> submittedFiles;

    // Construtor
    public Student(String name, List<File> submittedFiles){
        this.name = name;
        this.submittedFiles = submittedFiles;
    }

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<File> getSubmittedFiles() {
        return submittedFiles;
    }

    public void setSubmittedFiles(List<File> submittedFiles) {
        this.submittedFiles = submittedFiles;
    }

    // Método utilitário para adicionar um arquivo
    public void addSubmittedFile(File file) {
        this.submittedFiles.add(file);
    }
}
