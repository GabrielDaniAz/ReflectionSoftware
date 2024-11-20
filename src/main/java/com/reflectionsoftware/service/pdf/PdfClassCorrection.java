package com.reflectionsoftware.service.pdf;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.reflectionsoftware.model.result.correction.exercise.clazz.ClassCorrection;
import com.reflectionsoftware.service.pdf.specification.PdfConstructor;
import com.reflectionsoftware.service.pdf.specification.PdfField;
import com.reflectionsoftware.service.pdf.specification.PdfMethod;

public class PdfClassCorrection {

    public static void addClassCorrection(Document document, ClassCorrection classCorrection) {
        Paragraph classTitle = new Paragraph("Classe `" + classCorrection.getClassName() + "`:")
                .setBold()
                .setFontSize(14)
                .setMarginTop(10)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(classTitle);

        PdfConstructor.addConstructorSection(document, classCorrection.getConstructorsCorrection());
        PdfField.addFieldSection(document, classCorrection.getFieldsCorrection());
        PdfMethod.addMethodSection(document, classCorrection.getMethodsCorrection());
    }
}
