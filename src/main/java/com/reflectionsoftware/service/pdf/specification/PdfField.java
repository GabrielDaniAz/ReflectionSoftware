package com.reflectionsoftware.service.pdf.specification;

import java.util.List;
import java.util.function.Function;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.FieldCorrection;
import com.reflectionsoftware.service.pdf.PdfTableService;

public class PdfField {

    public static void addFieldSection(Document document, List<FieldCorrection> fields) {
        Paragraph fieldTitle = new Paragraph("Atributos:")
                    .setBold()
                    .setFontSize(14)
                    .setMarginTop(7);
        document.add(fieldTitle);

        addMissingFields(document, fields);

        // Adiciona tabela de construtores
        List<String> headers = List.of("Atributo", "Visibilidade", "Modificador", "Tipo", "%", "Nota Total", "Nota Obtida");
        List<Function<FieldCorrection, String>> valueExtractors = List.of(
            field -> field.studentString(),
            field -> field.isVisibilityCorrect() ? "V" : "X",
            field -> field.isModifiersCorrect() ? "V" : "X",
            field -> field.isTypeCorrect() ? "V" : "X",
            field -> String.valueOf((int) Math.round(field.getPercentilGrade())),
            field -> String.format("%.2f", field.getTotalGrade()),
            field -> String.format("%.2f", field.gradeObtained())
        );

        PdfTableService.addTable(document, headers, fields, valueExtractors);
    }

    private static void addMissingFields(Document document, List<FieldCorrection> fieldCorrections) {
        List<String> missingFields = fieldCorrections.stream()
                .filter(fieldCorrection -> !fieldCorrection.exists())
                .map(correction -> correction.getFieldName())
                .toList();

        if (!missingFields.isEmpty()) {
            String missingFieldsText = String.join(", ", missingFields);
            Paragraph info = new Paragraph("Atributos ausentes: " + missingFieldsText);
            document.add(info);
        }
    }
}
