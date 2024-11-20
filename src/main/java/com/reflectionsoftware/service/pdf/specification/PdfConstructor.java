package com.reflectionsoftware.service.pdf.specification;

import java.util.List;
import java.util.function.Function;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.ConstructorCorrection;
import com.reflectionsoftware.service.pdf.PdfTableService;

public class PdfConstructor {

    public static void addConstructorSection(Document document, List<ConstructorCorrection> constructors) {
        Paragraph constructorTitle = new Paragraph("Construtores:")
                    .setBold()
                    .setFontSize(14)
                    .setMarginTop(7);
        document.add(constructorTitle);

        addMissingConstructors(document, constructors);

        // Adiciona tabela de construtores
        List<String> headers = List.of("Construtor", "Visibilidade", "Modificador", "Nome", "Parâmetros", "%");
        List<Function<ConstructorCorrection, String>> valueExtractors = List.of(
            constructor -> constructor.getTemplateConstructor().getParameterTypes(),
            constructor -> constructor.isVisibilityCorrect() ? "V" : "X", // Visibilidade
            constructor -> constructor.isModifiersCorrect() ? "V" : "X", // Modificador
            constructor -> constructor.isNameCorrect() ? "V" : "X", // Nome
            constructor -> constructor.isParameterTypesCorrect() ? "V" : "X", // Parâmetros
            constructor -> String.valueOf(constructor.getGrade())
        );

        PdfTableService.addTable(document, headers, constructors, valueExtractors);
    }

    private static void addMissingConstructors(Document document, List<ConstructorCorrection> constructorCorrections) {
        List<String> missingConstructors = constructorCorrections.stream()
                .filter(constructorCorrection -> !constructorCorrection.exists())
                .map(correction -> correction.getTemplateConstructor().toString())
                .toList();

        if (!missingConstructors.isEmpty()) {
            String missingConstructorsText = String.join(", ", missingConstructors);
            Paragraph info = new Paragraph("Construtores ausentes: " + missingConstructorsText);
            document.add(info);
        }
    }
}
