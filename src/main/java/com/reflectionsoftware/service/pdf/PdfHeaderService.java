package com.reflectionsoftware.service.pdf;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.UnitValue;
import java.time.LocalDate;

public class PdfHeaderService {

    public static void addHeader(Document document, String studentName, double grade) {
        // Título do relatório
        Paragraph title = new Paragraph("Relatório de Correção de Exercícios")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(title);

        // Tabela com duas colunas, onde a primeira tem menor largura
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 7})).useAllAvailableWidth();

        // Adiciona a célula para "Aluno"
        table.addCell(createCell("Aluno:"));
        table.addCell(createCell(studentName));

        // Adiciona a célula para "Data"
        table.addCell(createCell("Data:"));
        table.addCell(createCell(LocalDate.now().toString()));

        // Adiciona a célula para "Nota"
        table.addCell(createCell("Nota:"));
        table.addCell(createCell(String.format("%.2f", grade)));

        // Adiciona a tabela ao documento
        document.add(table);
    }

    // Método auxiliar para criar as células da tabela
    private static Cell createCell(String content) {
        return new Cell()
                .add(new Paragraph(content))
                .setTextAlignment(TextAlignment.LEFT)
                .setFontSize(12)
                .setMarginBottom(5);
    }
}
