package service;

import model.Report;

// Serviço que cria relatórios dos resultados em PDF, usando a biblioteca PDFGenerator.
public class ReportService {
    
    /**
     * Gera o relatório final de um aluno.
     * 
     * @param report Objeto Report contendo todos os detalhes da correção do aluno.
     */
    public static void generateReport(Report report) {
        System.out.println(report.generateReportDetails());
        // Adicionar lógica para salvar em arquivo ou PDF.
    }
}
