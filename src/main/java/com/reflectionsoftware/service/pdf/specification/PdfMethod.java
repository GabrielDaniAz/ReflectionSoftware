package com.reflectionsoftware.service.pdf.specification;

import java.util.List;
import java.util.function.Function;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.reflectionsoftware.model.result.correction.exercise.clazz.specification.MethodCorrection;
import com.reflectionsoftware.service.pdf.PdfTableService;

public class PdfMethod {
    public static void addMethodSection(Document document, List<MethodCorrection> methods) {
        Paragraph methodTitle = new Paragraph("Métodos:")
                    .setBold()
                    .setFontSize(14)
                    .setMarginTop(7);
        document.add(methodTitle);

        addMissingMethods(document, methods);

        // Adiciona tabela de construtores
        List<String> headers = List.of("Método", "Visibilidade", "Modificador", "Retorno", "Nome", "Parâmetros", "%");
        List<Function<MethodCorrection, String>> valueExtractors = List.of(
            method -> method.getMethodName(),
            method -> method.isVisibilityCorrect() ? "V" : "X",
            method -> method.isModifiersCorrect() ? "V" : "X",
            method -> method.isReturnTypeCorrect() ? "V" : "X", 
            method -> method.isNameCorrect() ? "V" : "X",
            method -> method.isParameterTypesCorrect() ? "V" : "X",
            method -> String.valueOf(method.getGrade())
        );

        PdfTableService.addTable(document, headers, methods, valueExtractors);
    }

    private static void addMissingMethods(Document document, List<MethodCorrection> methodCorrections) {
        List<String> missingMethods = methodCorrections.stream()
                .filter(methodCorrection -> !methodCorrection.exists())
                .map(correction -> correction.getMethodName())
                .toList();

        if (!missingMethods.isEmpty()) {
            String missingMethodsText = String.join(", ", missingMethods);
            Paragraph info = new Paragraph("Métodos ausentes: " + missingMethodsText);
            document.add(info);
        }
    }
}
