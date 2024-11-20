package com.reflectionsoftware.service.pdf;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.reflectionsoftware.model.result.correction.ReflectionResult;

import java.io.File;
import java.io.IOException;

public class PdfService {

    public static void generateCorrectionReport(String studentName, ReflectionResult reflectionResult, File pdfFile) {
        try {
            PdfWriter writer = new PdfWriter(pdfFile.getAbsolutePath());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(20, 20, 20, 20);

            PdfHeaderService.addHeader(document, studentName);
            PdfReflectionResultService.addReflectionResult(document, reflectionResult);

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
