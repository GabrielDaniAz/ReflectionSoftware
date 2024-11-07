package com.reflectionsoftware.service.pdf;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.reflectionsoftware.service.reflection.CorrectionService.ComparisonConstructor;
import com.reflectionsoftware.service.reflection.CorrectionService.ComparisonField;
import com.reflectionsoftware.service.reflection.CorrectionService.ComparisonMethod;
import com.reflectionsoftware.service.reflection.CorrectionService.ComparisonResult;
import com.reflectionsoftware.service.reflection.CorrectionService.ReflectionResult;
import com.reflectionsoftware.service.reflection.CorrectionService.StepResult;

import java.io.IOException;
import java.util.List;

public class PdfService {

    public static void generateCorrectionReport(String studentName, ReflectionResult reflectionResult, String outputPath) {
        try {
            PdfWriter writer = new PdfWriter(outputPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(20, 20, 20, 20);

            addHeader(document, studentName);
            addReflectionResult(document, reflectionResult);

            document.close();
            System.out.println("PDF gerado com sucesso em: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addHeader(Document document, String studentName) {
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
    

    private static void addReflectionResult(Document document, ReflectionResult reflectionResult) {
        for (StepResult stepResult : reflectionResult.getStepResults()) {
            Paragraph stepTitle = new Paragraph("Etapa " + stepResult.getStepName() + ":")
                    .setFontSize(16)
                    .setBold()
                    .setFontColor(ColorConstants.BLUE)
                    .setMarginTop(10);
            document.add(stepTitle);

            if (!stepResult.getMissingClasses().isEmpty()) {
                Paragraph missingClasses = new Paragraph("Classes ausentes: " + String.join(", ", stepResult.getMissingClasses()))
                        .setFontColor(ColorConstants.RED)
                        .setMarginBottom(10);
                document.add(missingClasses);
            }

            for (ComparisonResult comparisonResult : stepResult.getComparisonResults()) {
                addClassComparison(document, comparisonResult);
            }
        }
    }

    private static void addClassComparison(Document document, ComparisonResult comparisonResult) {
        Paragraph classTitle = new Paragraph("Classe: " + comparisonResult.getClassName())
                .setBold()
                .setFontSize(14)
                .setMarginTop(10);
        document.add(classTitle);

        Paragraph detail = new Paragraph(comparisonResult.getDetail())
                .setFontSize(12)
                .setItalic()
                .setMarginBottom(10);
        document.add(detail);

        addComparisonSection(document, "Atributos", comparisonResult.getComparisionFields());
        addComparisonSection(document, "Métodos", comparisonResult.getComparisionMethods());
        addComparisonSection(document, "Construtores", comparisonResult.getComparisionConstructors());
    }

    private static void addComparisonSection(Document document, String sectionTitle, List<?> comparisons) {
        Paragraph sectionHeader = new Paragraph(sectionTitle)
                .setBold()
                .setFontSize(13)
                .setMarginTop(5);
        document.add(sectionHeader);

        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 2, 8}))
                .useAllAvailableWidth()
                .setMarginBottom(10);

        table.addHeaderCell(new Cell().add(new Paragraph("Nome")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Status")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Detalhes")).setBold());

        for (Object comparison : comparisons) {
            if (comparison instanceof ComparisonField) {
                ComparisonField field = (ComparisonField) comparison;
                table.addCell(new Cell().add(new Paragraph(field.getFieldName())));
                table.addCell(new Cell().add(new Paragraph(field.isEqual() ? "Correto" : "Incorreto")).setFontColor(field.isEqual() ? ColorConstants.GREEN : ColorConstants.RED));
                table.addCell(new Cell().add(new Paragraph(field.getDetail())));
            } else if (comparison instanceof ComparisonMethod) {
                ComparisonMethod method = (ComparisonMethod) comparison;
                table.addCell(new Cell().add(new Paragraph(method.getMethodName())));
                table.addCell(new Cell().add(new Paragraph(method.isEqual() ? "Correto" : "Incorreto")).setFontColor(method.isEqual() ? ColorConstants.GREEN : ColorConstants.RED));
                table.addCell(new Cell().add(new Paragraph(method.getDetail())));
            } else if (comparison instanceof ComparisonConstructor) {
                ComparisonConstructor constructor = (ComparisonConstructor) comparison;
                table.addCell(new Cell().add(new Paragraph("Construtor")));
                table.addCell(new Cell().add(new Paragraph(constructor.isEqual() ? "Correto" : "Incorreto")).setFontColor(constructor.isEqual() ? ColorConstants.GREEN : ColorConstants.RED));
                table.addCell(new Cell().add(new Paragraph(constructor.getDetail())));
            }
        }

        document.add(table);
    }
}
