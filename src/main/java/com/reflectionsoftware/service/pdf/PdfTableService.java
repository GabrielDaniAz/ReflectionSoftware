package com.reflectionsoftware.service.pdf;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.reflectionsoftware.model.result.reflection.comparison.specification.ComparisonConstructor;
import com.reflectionsoftware.model.result.reflection.comparison.specification.ComparisonField;
import com.reflectionsoftware.model.result.reflection.comparison.specification.ComparisonMethod;

import java.util.List;

public class PdfTableService {

    public static void createTable(Document document, String sectionTitle, List<?> comparisons) {
        Paragraph sectionHeader = new Paragraph(sectionTitle)
                .setBold()
                .setFontSize(13)
                .setMarginTop(5);
        document.add(sectionHeader);
    
        if (comparisons.isEmpty()) {
            return;
        }
    
        float[] columnWidths;
        if (comparisons.get(0) instanceof ComparisonMethod) columnWidths = new float[]{2, 1, 1, 1, 1, 1, 2};
        else columnWidths = new float[]{2, 2, 2, 2, 2, 2};
        
        Table table = new Table(UnitValue.createPercentArray(columnWidths))
                .useAllAvailableWidth()
                .setMarginTop(5);
    
        if (comparisons.get(0) instanceof ComparisonField) {
            addFieldHeaders(table);
            addFieldRows(table, castList(comparisons, ComparisonField.class));
        } else if (comparisons.get(0) instanceof ComparisonMethod) {
            addMethodHeaders(table);
            addMethodRows(table, castList(comparisons, ComparisonMethod.class));
        } else if (comparisons.get(0) instanceof ComparisonConstructor) {
            addConstructorHeaders(table);
            addConstructorRows(table, castList(comparisons, ComparisonConstructor.class));
        }
    
        document.add(table);
    }
    
    @SuppressWarnings("unchecked")
    private static <T> List<T> castList(List<?> list, Class<T> clazz) {
        for (Object obj : list) {
            if (!clazz.isInstance(obj)) {
                throw new ClassCastException("A lista contém um elemento que não é do tipo " + clazz.getName());
            }
        }
        return (List<T>) list;
    }
    

    private static void addFieldHeaders(Table table) {
        table.addHeaderCell(new Cell().add(new Paragraph("Nome")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Declarado")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Visibilidade")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Modificador")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Tipo")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Acerto (%)")).setBold().setTextAlignment(TextAlignment.CENTER));
    }

    private static void addFieldRows(Table table, List<ComparisonField> fields) {
        for (ComparisonField field : fields) {
            table.addCell(new Cell().add(new Paragraph(field.getFieldName()).setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(field.exists() ? "V" : "X")
                    .setFontColor(field.exists() ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(field.isVisibilityCorrect() ? "V" : "X")
                    .setFontColor(field.isVisibilityCorrect() ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(field.isModifierCorrect() ? "V" : "X")
                    .setFontColor(field.isModifierCorrect() ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(field.isTypeCorrect() ? "V" : "X")
                    .setFontColor(field.isTypeCorrect() ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(field.getGrade() + "%")
                    .setFontColor((field.getGrade() > 60) ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
        }
    }

    private static void addMethodHeaders(Table table) {
        table.addHeaderCell(new Cell().add(new Paragraph("Nome")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Declarado")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Visibilidade")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Modificador")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Retorno")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Parâmetros")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Acerto (%)")).setBold().setTextAlignment(TextAlignment.CENTER));
    }

    private static void addMethodRows(Table table, List<ComparisonMethod> methods) {
        for (ComparisonMethod method : methods) {
            table.addCell(new Cell().add(new Paragraph(method.getMethodName()).setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(method.exists() ? "V" : "X")
                    .setFontColor(method.exists() ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(method.isVisibilityCorrect() ? "V" : "X")
                    .setFontColor(method.isVisibilityCorrect() ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(method.isModifierCorrect() ? "V" : "X")
                    .setFontColor(method.isModifierCorrect() ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(method.isReturnTypeCorrect() ? "V" : "X")
                    .setFontColor(method.isReturnTypeCorrect() ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(method.isParamTypesCorrect() ? "V" : "X")
                    .setFontColor(method.isParamTypesCorrect() ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(method.getGrade() + "%")
                    .setFontColor((method.getGrade() > 60) ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
        }
    }

    private static void addConstructorHeaders(Table table) {
        table.addHeaderCell(new Cell().add(new Paragraph("Esperado")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Declarado")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Visibilidade")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Modificador")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Parâmetros")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Acerto (%)")).setBold().setTextAlignment(TextAlignment.CENTER));
    }

    private static void addConstructorRows(Table table, List<ComparisonConstructor> constructors) {
        for (ComparisonConstructor constructor : constructors) {
            // table.addCell(new Cell().add(new Paragraph(constructor.getExpectedParamTypes()).setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(constructor.getExpected() + "\n" + constructor.getActual()).setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(constructor.exists() ? "V" : "X")
                    .setFontColor(constructor.exists() ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(constructor.isVisibilityCorrect() ? "V" : "X")
                    .setFontColor(constructor.isVisibilityCorrect() ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(constructor.isModifierCorrect() ? "V" : "X")
                    .setFontColor(constructor.isModifierCorrect() ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(constructor.isParamTypesCorrect() ? "V" : "X")
                    .setFontColor(constructor.isParamTypesCorrect() ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
            table.addCell(new Cell().add(new Paragraph(constructor.getGrade() + "%")
                    .setFontColor((constructor.getGrade() > 60) ? ColorConstants.GREEN : ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)));
        }
    }
}
