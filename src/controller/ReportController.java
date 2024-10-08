package controller;

import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import model.CompilationResult;
import model.Student;

// Controlador responsável por gerar relatórios para os alunos após a correção de suas provas.
public class ReportController {

    private List<Student> students;

    /**
     * Construtor da classe ReportController.
     * 
     * @param students A lista de alunos cujos relatórios serão gerados.
     */
    public ReportController(List<Student> students) {
        this.students = students;
    }

    /**
     * Inicia a geração de relatórios para todos os alunos da lista.
     * 
     * Para cada aluno, o método {@link #reportStudent(Student)} é chamado para gerar o relatório individual.
     */
    public void startReports() {
        for (Student student : students) {
            reportStudent(student);
        }
    }

    /**
     * Gera o relatório individual para um aluno.
     * 
     * Exibe informações sobre o sucesso da compilação, a quantidade de diagnósticos e os resultados da análise de reflexão.
     *
     * @param student O aluno para o qual o relatório será gerado.
     */
    private void reportStudent(Student student) {
        String name = student.getName();
        CompilationResult compilationResult = student.getCompilationResult();
        List<String> reflectionResults = student.getReflectionResults();

        System.out.println("#########################################");
        System.out.println("CORREÇÃO DO ALUNO " + name.toUpperCase());

        // Exibe o status da compilação
        boolean compilationSuccess = compilationResult.isSuccess();
        System.out.println("COMPILAÇÃO: " + (compilationSuccess ? "SUCESSO" : "FALHOU"));

        // Exibe a quantidade de diagnósticos gerados pela compilação
        int diagnosticCount = compilationResult.getDiagnostics().size();
        System.out.println("QUANTIDADE DE DIAGNÓSTICOS: " + diagnosticCount);

        // Se houver diagnósticos, exibe detalhes de cada um
        if (diagnosticCount > 0) {
            System.out.println("DETALHES DO DIAGNÓSTICO:");
            for (Diagnostic<? extends JavaFileObject> diagnostic : compilationResult.getDiagnostics()) {
                System.out.println(diagnostic.getKind() + ": " + diagnostic.getMessage(null));
                System.out.println("Linha: " + diagnostic.getLineNumber());
                System.out.println("Arquivo: " + diagnostic.getSource().getName());
            }
        }

        // Exibe os resultados da análise de reflexão
        if (!reflectionResults.isEmpty()) {
            System.out.println("RESULTADOS DA ANÁLISE DE REFLEXÃO:");
            for (String result : reflectionResults) {
                System.out.println(result);
            }
        } else {
            System.out.println("NENHUM RESULTADO DE REFLEXÃO DISPONÍVEL.");
        }

        System.out.println("#########################################");
    }
}
