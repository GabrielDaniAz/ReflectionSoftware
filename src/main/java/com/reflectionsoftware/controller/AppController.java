package com.reflectionsoftware.controller;

import java.io.IOException;

import com.reflectionsoftware.model.criteria.Criteria;
import com.reflectionsoftware.service.CompilationService;
import com.reflectionsoftware.service.ReflectionService;

// Controla o fluxo do software, orquestrando os serviços necessários para buscar os arquivos dos alunos, instanciar objetos Student, e iniciar a correção.
public class AppController {
    private String rootDirectory;
    private String jsonFilePath;
    private String outputPdfFilePath;
    private int untilStep;

    public AppController(String rootDirectory, String jsonFilePath, String outputPdfFilePath, int untilStep) {
        this.rootDirectory = rootDirectory;
        this.jsonFilePath = jsonFilePath;
        this.outputPdfFilePath = outputPdfFilePath;
        this.untilStep = untilStep;
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
        Criteria criteria = new CriteriaManager(jsonFilePath, untilStep).getCriteria();

        // 1. Obter arquivos .java dos alunos
        FileController fileController = new FileController(rootDirectory);

        // 2. Criar objetos Student
        StudentController studentController = new StudentController(fileController.getJavaFilesFromDirectory());

        // 3. Iniciar a correção para os alunos
        new CorrectionController(studentController.getStudents(), new CompilationService(), new ReflectionService(criteria));

        // // 4. Gerar relatório em PDF
        // new PdfController(studentController.getStudents(), outputPdfFilePath);
    }
}
