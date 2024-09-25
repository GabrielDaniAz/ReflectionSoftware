package service;

import java.io.File;

import controller.ReportManager;
import model.CompilationResult;
import model.FileResult;
import model.ReflectionResult;
import model.Report;
import model.Student;

// Executa a correção dos códigos submetidos pelos alunos, aplicando as regras de correção e utilizando a API de reflexão para análise dos arquivos .java.
public class CorrectionService {
    
    /**
     * Realiza a correção para um aluno, chamando os métodos de compilação e reflexão.
     * 
     * @param student Student representando o aluno e seus arquivos .java.
     * @throws Exception 
     */
    public static void correctStudent(Student student) throws Exception {
        Report report = new Report(student.getName());

        for (File javaFile : student.getSubmittedFiles()) {
            // 1. Criar classe de Correção do arquivo Java
            FileResult fileResult = new FileResult(javaFile);

            // 2. Realizar a compilação
            CompilationResult compilationResult = CompilationService.compileJavaFile(javaFile);
            fileResult.setCompilationResult(compilationResult);

            // 3. Analisar a classe usando Reflection
            if (compilationResult.isSuccess()) {
                ReflectionResult reflectionResult = ReflectionService.analyzeFile(javaFile);
                fileResult.setReflectionResult(reflectionResult);
            }

            // 4. Adicionar o resultado FileResult em Report
            report.addFileResult(fileResult);
        }

        // 5. Salvar em ReportManager
        ReportManager.addReport(report);
    }
}
