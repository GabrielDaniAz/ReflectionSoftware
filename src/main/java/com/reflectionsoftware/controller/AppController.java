package com.reflectionsoftware.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.service.CompilationService;
import com.reflectionsoftware.service.CorrectionCriteriaManager;
import com.reflectionsoftware.service.ReflectionService;

// Controla o fluxo do software, orquestrando os serviços necessários para buscar os arquivos dos alunos, instanciar objetos Student, e iniciar a correção.
public class AppController {

    private FileController fileController;
    private StudentController studentController;
    private CorrectionController correctionController;
    private String outputPdfPath;

    /**
     * Construtor da classe AppController.
     * 
     * Este construtor inicializa os controladores necessários e carrega os critérios de correção a partir de um arquivo JSON.
     * 
     * @param rootDirectory O caminho do diretório raiz onde os arquivos dos alunos estão localizados.
     * @param jsonFilePath O caminho do arquivo JSON que contém os critérios de correção.
     * @param outputPdfPath O caminho onde o relatório em PDF deve ser salvo.
     */
    public AppController(String rootDirectory, String jsonFilePath, String outputPdfPath) {
        // Usando JsonConverter para ler o arquivo JSON
        CorrectionCriteriaManager.fromJson(jsonFilePath);

        fileController = new FileController(rootDirectory);

        this.outputPdfPath = outputPdfPath;
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
        // 1. Obter arquivos .java dos alunos
        Map<File, List<File>> javaFilesFromDirectory = fileController.getJavaFilesFromDirectory();

        // 2. Criar objetos Student
        studentController = new StudentController(javaFilesFromDirectory);        
        List<Student> students = studentController.getStudents();

        // 3. Iniciar a correção para os alunos
        correctionController = new CorrectionController(students, new CompilationService(), new ReflectionService());
        correctionController.startCorrection();

        // 4. Gerar relatório em PDF
        PdfController pdf = new PdfController(students, outputPdfPath);
        pdf.startReports();
    }
}
