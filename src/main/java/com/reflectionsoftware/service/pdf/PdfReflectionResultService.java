package com.reflectionsoftware.service.pdf;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.reflectionsoftware.model.result.reflection.ReflectionResult;
import com.reflectionsoftware.model.result.reflection.comparison.ComparisonTemplate;
import com.reflectionsoftware.model.result.reflection.comparison.ComparisonClass;

public class PdfReflectionResultService {

    public static void addReflectionResult(Document document, ReflectionResult reflectionResult) {
        for (ComparisonTemplate exercise : reflectionResult.getExercises()) {
            Paragraph stepTitle = new Paragraph("Etapa " + exercise.getExerciseName() + ":")
                    .setFontSize(16)
                    .setBold()
                    .setFontColor(ColorConstants.BLUE)
                    .setMarginTop(10);
            document.add(stepTitle);

            if (!exercise.getMissingClasses().isEmpty()) {
                Paragraph missingClasses = new Paragraph("Classes ausentes: " + String.join(", ", exercise.getMissingClasses()))
                        .setFontColor(ColorConstants.RED)
                        .setMarginBottom(10);
                document.add(missingClasses);
            }

            for (ComparisonClass clazzResult : exercise.getComparisonClasses()) {
                PdfClassComparisonService.addClassComparison(document, clazzResult);
            }
        }
    }
}
