package com.reflectionsoftware.service.pdf;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.reflectionsoftware.model.result.reflection.comparison.ComparisonClass;
import com.reflectionsoftware.model.result.reflection.comparison.specification.ComparisonConstructor;
import com.reflectionsoftware.model.result.reflection.comparison.specification.ComparisonField;
import com.reflectionsoftware.model.result.reflection.comparison.specification.ComparisonMethod;

import java.util.List;

public class PdfClassComparisonService {

    public static void addClassComparison(Document document, ComparisonClass comparisonResult) {
        Paragraph classTitle = new Paragraph("Classe: " + comparisonResult.getClassName())
                .setBold()
                .setFontSize(14)
                .setMarginTop(10);
        document.add(classTitle);

        addFieldSection(document, comparisonResult.getComparisionFields());
        addMethodSection(document, comparisonResult.getComparisionMethods());
        addConstructorSection(document, comparisonResult.getComparisionConstructors());
    }

    private static void addFieldSection(Document document, List<ComparisonField> fields) {
        addInformations(document, fields);
        PdfTableService.createTable(document, "Atributos", fields);
    }

    private static void addMethodSection(Document document, List<ComparisonMethod> methods) {
        addInformations(document, methods);
        PdfTableService.createTable(document, "Métodos", methods);
    }

    private static void addConstructorSection(Document document, List<ComparisonConstructor> constructors) {
        addInformations(document, constructors);
        PdfTableService.createTable(document, "Construtores", constructors);
    }

    private static void addInformations(Document document, List<?> comparisonList) {
        for (Object comparison : comparisonList) {
            if (comparison instanceof ComparisonField) {
                ComparisonField fieldComparison = (ComparisonField) comparison;
                Paragraph info = new Paragraph("Atributo:")
                        .add("\nEsperado: " + fieldComparison.getExpected())
                        .add("\nAluno: " + fieldComparison.getActual());
                document.add(info);
            } else if (comparison instanceof ComparisonMethod) {
                ComparisonMethod methodComparison = (ComparisonMethod) comparison;
                Paragraph info = new Paragraph("Método:")
                        .add("\nEsperado: " + methodComparison.getExpected())
                        .add("\nAluno: " + methodComparison.getActual());
                document.add(info);
            } else if (comparison instanceof ComparisonConstructor) {
                ComparisonConstructor constructorComparison = (ComparisonConstructor) comparison;
                Paragraph info = new Paragraph("Construtor:")
                        .add("\nEsperado: " + constructorComparison.getExpected())
                        .add("\nAluno: " + constructorComparison.getActual());
                document.add(info);
            } else {
                Paragraph unknownInfo = new Paragraph("Tipo de comparação desconhecido.");
                document.add(unknownInfo);
            }
        }
    }
    
}
