package com.reflectionsoftware.controller;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;
import java.util.Map;
import java.io.IOException;
import java.time.LocalDate;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.model.result.Result;
import com.reflectionsoftware.model.result.compilation.CompilationResult;
import com.reflectionsoftware.model.result.reflection.CorrectionResult;

// Controlador responsável pela geração de relatórios em PDF para os alunos.
public class PdfController {

    private static final float[] TABLE_COLUMN_WIDTHS = {1, 3}; // Larguras das colunas da tabela
    private static final String COURSE_NAME = "POO"; // Nome do curso
    private static final String REPORT_TITLE = "Relatório de Correção"; // Título do relatório
    private static final String COMPILATION_REPORT_TITLE = "Relatório de Compilação e Reflexão"; // Título do relatório de compilação
    private static final String COMPILATION_SUCCESS_MSG = "Compilação: Sucesso ✅"; // Mensagem de sucesso de compilação
    private static final String COMPILATION_FAIL_MSG = "Compilação: Falhou ❌"; // Mensagem de falha de compilação
    private static final String COMPILATION_RESULT_TITLE = "Resultado da Compilação"; // Título do resultado da compilação
    private static final String REFLECTION_RESULT_TITLE = "Resultado da Análise de Reflexão"; // Título do resultado da análise de reflexão
    private static final String NO_REFLECTION_RESULT_MSG = "Nenhum resultado de reflexão disponível."; // Mensagem caso não haja resultados de reflexão

    private java.util.List<Student> students; // Lista de alunos
    private String outputDirectory; // Diretório de saída para os relatórios

    /**
     * Construtor da classe PdfController.
     *
     * Inicializa o controlador com a lista de alunos e o diretório de saída onde os relatórios serão gerados.
     *
     * @param students A lista de alunos para os quais os relatórios serão gerados.
     * @param outputDirectory O diretório onde os relatórios em PDF serão salvos.
     * @throws IOException Se ocorrer um erro ao acessar o diretório de saída.
     */
    public PdfController(java.util.List<Student> students, String outputDirectory) throws IOException {
        this.students = students;
        this.outputDirectory = outputDirectory;
        startReports();
    }

    /**
     * Inicia a geração de relatórios para todos os alunos.
     *
     * Este método percorre a lista de alunos e tenta criar um relatório PDF para cada um deles.
     * Em caso de erro, uma mensagem de erro é impressa no console.
     */
    public void startReports() {
        for (Student student : students) {
            try {
                createReportForStudent(student);
            } catch (IOException e) {
                System.err.println("Erro ao criar relatório para o aluno " + student.getName() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Cria um relatório em PDF para um aluno específico.
     *
     * Este método gera um arquivo PDF que contém informações do aluno, resultados de compilação e análise de reflexão.
     *
     * @param student O aluno para o qual o relatório será gerado.
     * @throws IOException Se ocorrer um erro durante a criação do arquivo PDF.
     */
    private void createReportForStudent(Student student) throws IOException {
        String filePath = outputDirectory + File.separator + student.getName() + "_relatorio.pdf";
    
        PdfWriter writer = null;
        PdfDocument pdf = null;
        Document document = null;
    
        try {
            writer = new PdfWriter(filePath);
            pdf = new PdfDocument(writer);
            document = new Document(pdf);
    
            // Criando novas instâncias de fontes para o documento
            PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont fontRegular = PdfFontFactory.createFont(StandardFonts.HELVETICA);
    
            // Cabeçalho principal
            document.add(new Paragraph(REPORT_TITLE)
                    .setFont(fontBold)
                    .setFontSize(18)
                    .setBold()
                    .setMarginBottom(20));
    
            // Tabela de Informações do Aluno
            Table table = new Table(UnitValue.createPercentArray(TABLE_COLUMN_WIDTHS)).useAllAvailableWidth();
    
            table.addCell(new Cell().add(new Paragraph("Aluno:").setFont(fontBold)));
            table.addCell(new Cell().add(new Paragraph(student.getName()).setFont(fontRegular)));
    
            table.addCell(new Cell().add(new Paragraph("Curso:").setFont(fontBold)));
            table.addCell(new Cell().add(new Paragraph(COURSE_NAME).setFont(fontRegular)));
    
            table.addCell(new Cell().add(new Paragraph("Data:").setFont(fontBold)));
            table.addCell(new Cell().add(new Paragraph(LocalDate.now().toString()).setFont(fontRegular)));
    
            document.add(table.setMarginBottom(20));
    
            // Seção do Relatório de Compilação e Reflexão
            document.add(new Paragraph(COMPILATION_REPORT_TITLE)
                    .setFont(fontBold)
                    .setFontSize(16)
                    .setMarginBottom(15));
    
            // Geração dos relatórios de compilação e reflexão
            Result result = student.getResult();
            generateCompilationReport(document, result.getCompilationResult());
            generateReflectionReport(document, result.getReflectionResult().getCorrectionResults());
    
        } catch (IOException e) {
            System.err.println("Erro ao criar relatório para o aluno " + student.getName() + ": " + e.getMessage());
        } finally {
            if (document != null) {
                document.close(); // Certifique-se de fechar o documento aqui
            }
            if (pdf != null && !pdf.isClosed()) {
                pdf.close(); // Fechar o documento PDF corretamente
            }
            if (writer != null) {
                writer.close(); // Certifique-se de que o writer seja fechado
            }
        }
    }

    /**
     * Gera o relatório de compilação para o documento PDF.
     *
     * Este método adiciona informações sobre o resultado da compilação ao documento.
     *
     * @param document O documento PDF ao qual o relatório de compilação será adicionado.
     * @param compilationResult O resultado da compilação que contém informações sobre o sucesso ou falha da compilação.
     */
    private void generateCompilationReport(Document document, CompilationResult compilationResult) {
        document.add(new Paragraph(COMPILATION_RESULT_TITLE)
                .setFontSize(14)
                .setBold()
                .setMarginBottom(10));

        boolean compilationSuccess = compilationResult.isSuccess();
        document.add(new Paragraph(compilationSuccess ? COMPILATION_SUCCESS_MSG : COMPILATION_FAIL_MSG)
                .setMarginBottom(10));

        int diagnosticCount = compilationResult.getDiagnostics().size();
        document.add(new Paragraph("Quantidade de diagnósticos: " + diagnosticCount).setMarginBottom(10));

        if (diagnosticCount > 0) {
            document.add(new Paragraph("Detalhes do diagnóstico:").setBold().setMarginBottom(5));
            List diagnosticList = new List();

            for (Diagnostic<? extends JavaFileObject> diagnostic : compilationResult.getDiagnostics()) {
                diagnosticList.add(new ListItem(
                        diagnostic.getKind() + ": " + diagnostic.getMessage(null) +
                        " | Linha: " + diagnostic.getLineNumber() +
                        " | Arquivo: " + diagnostic.getSource().getName()
                ));
            }

            document.add(diagnosticList);
        }
        document.add(new Paragraph("\n")); // Quebra de linha
    }

    /**
     * Gera o relatório de reflexão para o documento PDF.
     *
     * Este método adiciona os resultados da análise de reflexão ao documento.
     *
     * @param document O documento PDF ao qual o relatório de reflexão será adicionado.
     * @param results A lista de resultados da correção que contém informações sobre as classes analisadas.
     */
    private void generateReflectionReport(Document document, java.util.List<CorrectionResult> results) {
        document.add(new Paragraph(REFLECTION_RESULT_TITLE)
                .setFontSize(14)
                .setBold()
                .setMarginBottom(10));

        if (results != null && !results.isEmpty()) {
            for (CorrectionResult result : results) {
                document.add(new Paragraph("Passo: " + result.getStep())
                        .setBold()
                        .setFontSize(12)
                        .setMarginBottom(5));

                // Exibir erros e sucessos dos atributos
                addSectionToDocument(document, "Atributos com Erros", result.getAttributesErrors());
                addSectionToDocument(document, "Atributos com Sucessos", result.getAttributesSuccesses());

                // Exibir erros e sucessos dos métodos
                addSectionToDocument(document, "Métodos com Erros", result.getMethodsErrors());
                addSectionToDocument(document, "Métodos com Sucessos", result.getMethodsSuccesses());

                // Exibir erros e sucessos dos construtores
                addSectionToDocument(document, "Construtores com Erros", result.getConstructorsErrors());
                addSectionToDocument(document, "Construtores com Sucessos", result.getConstructorsSuccesses());

                // Exibir o resumo da correção
                document.add(new Paragraph("Resumo da Correção:").setBold());
                for (Map.Entry<String, String> entry : result.getResume().entrySet()) {
                    document.add(new Paragraph("Classe: " + entry.getKey()));
                    document.add(new Paragraph("Resumo: " + entry.getValue()).setItalic());
                }

                document.add(new Paragraph("\n")); // Quebra de linha após cada bloco de correção
            }
        } else {
            document.add(new Paragraph(NO_REFLECTION_RESULT_MSG)
                    .setItalic()
                    .setMarginBottom(10));
        }
    }

    /**
     * Adiciona uma seção de erros ou sucessos ao documento PDF.
     *
     * @param document O documento PDF.
     * @param sectionTitle O título da seção (ex: "Atributos com Erros").
     * @param messages O mapa contendo as classes e suas respectivas mensagens de erro ou sucesso.
     */
    private void addSectionToDocument(Document document, String sectionTitle, Map<String, java.util.List<String>> messages) {
        document.add(new Paragraph(sectionTitle).setBold());

        if (messages.isEmpty()) {
            document.add(new Paragraph("Nenhum encontrado.").setItalic());
        } else {
            for (Map.Entry<String, java.util.List<String>> entry : messages.entrySet()) {
                document.add(new Paragraph("Classe: " + entry.getKey()));
                for (String message : entry.getValue()) {
                    document.add(new Paragraph("  - " + message));
                }
            }
        }

        document.add(new Paragraph("\n")); // Quebra de linha após a seção
    }

}
