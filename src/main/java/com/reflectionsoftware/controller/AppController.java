package com.reflectionsoftware.controller;

import java.io.IOException;

import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.model.result.Result;
import com.reflectionsoftware.model.result.reflection.CorrectionResult;

// Controla o fluxo do software, orquestrando os serviços necessários para buscar os arquivos dos alunos, instanciar objetos Student, e iniciar a correção.
public class AppController {
    private String rootDirectory;
    private String jsonFilePath;
    private String outputPdfFilePath;
    private String correctionStep;

    /**
     * Construtor da classe AppController.
     * 
     * Este construtor inicializa os controladores necessários e carrega os critérios de correção a partir de um arquivo JSON.
     * 
     * @param rootDirectory O caminho do diretório raiz onde os arquivos dos alunos estão localizados.
     * @param jsonFilePath O caminho do arquivo JSON que contém os critérios de correção.
     * @param outputPdfPath O caminho onde o relatório em PDF deve ser salvo.
     * @throws IOException 
     */
    public AppController(String rootDirectory, String jsonFilePath, String outputPdfFilePath, String correctionStep) {
        this.rootDirectory = rootDirectory;
        this.jsonFilePath = jsonFilePath;
        this.outputPdfFilePath = outputPdfFilePath;
        this.correctionStep = correctionStep;
    }

    /**
     * Inicia o processo de correção a partir do caminho do diretório.
     * 
     * O processo envolve:
     * 1. Buscar os arquivos .java dos alunos.
     * 2. Criar os objetos Student.
     * 3. Iniciar o processo de correção para os alunos.
     * 4. Gerar os relatórios da correção.
     * 
     * @throws Exception Se ocorrer algum erro durante o processo de correção.
     */
    public void start() throws Exception {
        // 1. Obter o critério de correção recebido pelo JSON
        new CriteriaManager(jsonFilePath, correctionStep);

        // 1. Obter arquivos .java dos alunos
        FileController fileController = new FileController(rootDirectory);

        // 2. Criar objetos Student
        StudentController studentController = new StudentController(fileController.getJavaFilesFromDirectory());

        // 3. Iniciar a correção para os alunos
        new CorrectionController(studentController.getStudents());

        // Teste
        for (Student student : studentController.getStudents()) {
            System.out.println("Aluno: " + student.getName());
            Result r = student.getResult();
            for (CorrectionResult result : r.getReflectionResult().getCorrectionResults()) {
                System.out.println(result.toString());
            }
        }

        // 4. Gerar relatório em PDF
        new PdfController(studentController.getStudents(), outputPdfFilePath);
    }
}
