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
        List<String> headers = List.of("Atributo", "Visibilidade", "Modificador", "Tipo", "Nome", "%");
        List<Function<FieldCorrection, String>> valueExtractors = List.of(
            field -> field.getFieldName(),
            field -> field.isVisibilityCorrect() ? "V" : "X",
            field -> field.isModifiersCorrect() ? "V" : "X",
            field -> field.isTypeCorrect() ? "V" : "X", 
            field -> field.isNameCorrect() ? "V" : "X",
            field -> String.valueOf(field.getGrade())
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
