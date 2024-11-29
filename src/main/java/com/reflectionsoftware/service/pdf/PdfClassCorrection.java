package com.reflectionsoftware.service.pdf;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.reflectionsoftware.model.result.correction.exercise.clazz.ClassCorrection;

public class PdfClassCorrection {

    public static void addClassCorrection(Document document, ClassCorrection classCorrection) {
        Paragraph classTitle = new Paragraph("Classe `" + classCorrection.getTemplate().getSimpleName() + "`:")
                .setBold()
                .setFontSize(14)
                .setMarginTop(10)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(classTitle);

        PdfElement.addElementSection(document, classCorrection.getCorrectedElements());
    }
}
