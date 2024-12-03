package com.reflectionsoftware.service.pdf;

import java.util.List;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.reflectionsoftware.model.result.correction.SpecificationElement;
import com.reflectionsoftware.model.result.correction.exercise.clazz.ClassCorrection;
import com.reflectionsoftware.util.reflection.element.ElementFilter;
import com.reflectionsoftware.util.reflection.element.ElementFilter.ElementType;

public class PdfClassCorrection {

    public static void addClassCorrection(Document document, ClassCorrection classCorrection) {

        Paragraph classTitle = new Paragraph("Classe `" + classCorrection.getTemplate().getSimpleName() + "`:")
                .setBold()
                .setFontSize(14)
                .setMarginTop(10)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(classTitle);

        if (!classCorrection.getMissingElements().isEmpty()) {
            List<SpecificationElement<?>> missingElements = classCorrection.getMissingElements();

            List<SpecificationElement<?>> constructors = ElementFilter.getElementsByType(missingElements, ElementType.CONSTRUCTOR);
            List<SpecificationElement<?>> fields = ElementFilter.getElementsByType(missingElements, ElementType.FIELD);
            List<SpecificationElement<?>> methods = ElementFilter.getElementsByType(missingElements, ElementType.METHOD);

            Paragraph absent = new Paragraph("Ausentes:")
                    .setFontColor(ColorConstants.RED)
                    .setMarginBottom(5);
            document.add(absent);

            addMissingElements("Construtores", constructors, document);
            addMissingElements("Atributos", fields, document);
            addMissingElements("Métodos", methods, document);
        }

        PdfElement.addElementSection(document, classCorrection.getCorrectedElements());
    }

    private static void addMissingElements(String title, List<SpecificationElement<?>> elements, Document document) {
        if(elements.isEmpty()) return;
        Paragraph missingElements = new Paragraph(title + ": " + String.join(", ", elements.toString()))
                    .setFontColor(ColorConstants.RED)
                    .setMarginBottom(10);
        document.add(missingElements);
    }
}
