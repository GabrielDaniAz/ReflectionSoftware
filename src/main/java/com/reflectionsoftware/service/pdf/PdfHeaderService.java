package com.reflectionsoftware.service.pdf;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

public class PdfHeaderService {

    public static void addHeader(Document document, String studentName) {
        Paragraph title = new Paragraph("Relatório de Correção de Exercícios")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(title);
    
        Paragraph studentInfo = new Paragraph("Aluno: " + studentName)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginBottom(10);
        document.add(studentInfo);
    
        Paragraph subtitle = new Paragraph("Este relatório fornece detalhes sobre a correção das classes de acordo com as especificações.")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12)
                .setMarginBottom(20);
        document.add(subtitle);
    }
}
