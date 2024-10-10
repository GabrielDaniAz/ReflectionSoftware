package com.reflectionsoftware.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.reflectionsoftware.model.Student;

// Controla o fluxo do software, orquestrando os serviços necessários para buscar os arquivos dos alunos, instanciar objetos Student, e iniciar a correção.
public class AppController {

    private FileController fileController;
    private StudentController studentController;
    private CorrectionController correctionController;

    /**
     * Construtor da classe AppController.
     * 
     * @param rootDirectory O caminho do diretório raiz onde os arquivos dos alunos estão localizados.
     */
    public AppController(String rootDirectory) {
        fileController = new FileController(rootDirectory);
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
        correctionController = new CorrectionController(students);
        correctionController.startCorrection();

        // 4. Iniciar os prints das correções
        ReportController reportController = new ReportController(students);
        reportController.startReports();
    }
}
