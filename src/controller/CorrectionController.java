package controller;

import model.CompilationResult;
import model.Student;
import service.CompilationService;
import service.ReflectionAnalysisService;

import java.io.File;
import java.util.List;

// Controlador responsável por orquestrar a correção de múltiplos alunos.
public class CorrectionController {

    private List<Student> students;
    private CompilationService compilationService;
    private ReflectionAnalysisService reflectionAnalysisService;

    /**
     * Construtor da classe CorrectionController.
     * 
     * @param students A lista de alunos que terão seus códigos corrigidos.
     */
    public CorrectionController(List<Student> students) {
        this.students = students;
        this.compilationService = new CompilationService();
        this.reflectionAnalysisService = new ReflectionAnalysisService();
    }

    /**
     * Inicia o processo de correção para todos os alunos.
     * 
     * Para cada aluno, o método {@link #correctStudent(Student)} é chamado para realizar a correção individualmente.
     */
    public void startCorrection() {
        for (Student student : students) {
            try {
                correctStudent(student);
            } catch (Exception e) {
                // Lidar com erros específicos de cada aluno
                System.err.println("Erro ao corrigir o aluno " + student.getName() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Realiza a correção de um aluno individual, compilando os arquivos Java submetidos e reportando o resultado.
     * Se a compilação for bem-sucedida, os arquivos .class gerados são analisados pelo serviço de reflexão.
     *
     * @param student O aluno cujos arquivos de código serão corrigidos.
     * @throws Exception Caso ocorra algum erro durante a compilação ou análise dos arquivos.
     */
    private void correctStudent(Student student) throws Exception {
        // Diretório de saída para os arquivos .class compilados
        File binDirectory = new File(student.getRootDirectory(), "bin");

        // Compilando os arquivos Java do aluno
        CompilationResult compilationResult = compilationService.compile(student.getSubmittedFiles(), binDirectory);
        student.setCompilationResult(compilationResult);

        // Se a compilação for bem-sucedida, realiza a análise de reflexão das classes
        if (compilationResult.isSuccess()) {
            // Obtém os arquivos .class gerados pela compilação
            List<File> compiledClassFiles = student.getCompilationResult().getCompiledFiles();

            // Analisando as classes compiladas
            List<String> analysisResults = reflectionAnalysisService.analyzeClasses(compiledClassFiles, binDirectory);
            student.setReflectionResults(analysisResults);

        } else {
            // Reporta o erro caso a compilação falhe
            System.err.println("Compilação falhou para o aluno " + student.getName());
        }
    }
}
