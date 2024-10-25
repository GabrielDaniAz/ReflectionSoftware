package com.reflectionsoftware.controller;

import java.io.File;
import java.util.List;

import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.model.result.Result;
import com.reflectionsoftware.model.result.compilation.CompilationResult;
import com.reflectionsoftware.model.result.reflection.ReflectionResult;
import com.reflectionsoftware.service.CompilationService;
import com.reflectionsoftware.service.ReflectionService;

// Controlador responsável por orquestrar a correção de múltiplos alunos.
public class CorrectionController {

    private List<Student> students;
    private CompilationService compilationService;
    private ReflectionService reflectionService;

    /**
     * Construtor da classe CorrectionController.
     *
     * Inicializa o controlador com a lista de alunos e os serviços de compilação e reflexão que serão utilizados para a correção.
     *
     * @param students A lista de alunos que terão seus códigos corrigidos.
     * @param compilationService O serviço de compilação que será utilizado.
     * @param reflectionService O serviço de reflexão que será utilizado.
     */
    public CorrectionController(List<Student> students) {
        this.students = students;
        compilationService = new CompilationService();
        reflectionService = new ReflectionService();
        startCorrection();
    }

    /**
     * Inicia o processo de correção para todos os alunos.
     *
     * Este método percorre a lista de alunos e tenta corrigir o código de cada um.
     * Em caso de erro, uma mensagem de erro é impressa no console.
     */
    public void startCorrection() {
        for (Student student : students) {
            try {
                correctStudent(student);
            } catch (Exception e) {
                System.err.println("Erro ao corrigir o aluno " + student.getName() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Realiza a correção de um aluno individual, compilando os arquivos Java submetidos e reportando o resultado.
     *
     * Este método compila os arquivos Java do aluno e, se a compilação for bem-sucedida,
     * utiliza reflexão para analisar as classes geradas e armazenar os resultados na instância do aluno.
     *
     * @param student O aluno cujos arquivos de código serão corrigidos.
     * @throws Exception Caso ocorra algum erro durante a compilação ou análise dos arquivos.
     */
    private void correctStudent(Student student) throws Exception {
        File binDirectory = new File(student.getRootDirectory(), "bin");

        // Compila os arquivos submetidos pelo aluno
        CompilationResult compilationResult = compilationService.compile(student.getSubmittedFiles(), binDirectory);
        ReflectionResult reflectionResult = null;

        // Se a compilação for bem-sucedida, usa reflexão para obter as classes
        if (compilationResult.isSuccess()) {
            List<File> compiledClassFiles = compilationResult.getCompiledFiles();
            List<Class<?>> classes = reflectionService.getClasses(compiledClassFiles, binDirectory);
            reflectionResult = new ReflectionResult(classes);
        }

        // Define o resultado da correção no objeto Student
        student.setResult(new Result(compilationResult, reflectionResult));
    }
}
