package com.reflectionsoftware.service.pdf;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.reflectionsoftware.model.result.correction.ReflectionResult;
import com.reflectionsoftware.model.result.correction.exercise.ExerciseCorrection;
import com.reflectionsoftware.model.result.correction.exercise.clazz.ClassCorrection;

public class PdfReflectionResultService {

    public static void addReflectionResult(Document document, ReflectionResult reflectionResult) {
        for (ExerciseCorrection exercise : reflectionResult.getExercises()) {
            Paragraph stepTitle = new Paragraph("Exercício `" + exercise.getExerciseName() + "`:")
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

            System.out.println("tamanho da lista corrigida: " + exercise.getCorrectedClasses().size());
            for (ClassCorrection classCorrection : exercise.getCorrectedClasses()) {
                System.out.println(classCorrection.getGrade());
                PdfClassCorrection.addClassCorrection(document, classCorrection);
            }
        }
    }
}
