package com.javacorrige.service.pdf;

import java.util.List;
import java.util.function.Function;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.javacorrige.model.result.correction.SpecificationElement;
import com.javacorrige.util.reflection.element.ElementFilter;
import com.javacorrige.util.reflection.element.ElementFilter.ElementType;

public class PdfElement {

    public static void addElementSection(Document document, List<SpecificationElement<?>> elements) {

        // Filtra elementos do tipo Construtor, Campo e Método
        List<SpecificationElement<?>> constructors = ElementFilter.getElementsByType(elements, ElementType.CONSTRUCTOR);
        List<SpecificationElement<?>> fields = ElementFilter.getElementsByType(elements, ElementType.FIELD);
        List<SpecificationElement<?>> methods = ElementFilter.getElementsByType(elements, ElementType.METHOD);

        // Adiciona o título da seção (diferente para cada tipo de elemento)
        if(!constructors.isEmpty()){
            addSectionTitle(document, "Construtores", constructors);
            addTableForConstructors(document, constructors);
        }
        if(!fields.isEmpty()){
            addSectionTitle(document, "Atributos", fields);
            addTableForFields(document, fields);
        }
        if(!methods.isEmpty()){
            addSectionTitle(document, "Métodos", methods);
            addTableForMethods(document, methods);
        }
    }

    // Método auxiliar para adicionar o título da seção
    private static void addSectionTitle(Document document, String sectionTitle, List<SpecificationElement<?>> elements) {
        Paragraph sectionTitleParagraph = new Paragraph(sectionTitle)
                .setBold()
                .setFontSize(14)
                .setMarginTop(7);
        document.add(sectionTitleParagraph);
    }

    // Adiciona tabela para os construtores
    private static void addTableForConstructors(Document document, List<SpecificationElement<?>> elements) {
        List<String> headers = List.of("Construtor", "Visibilidade", "Modificador", "Parâmetros", "Nota");

        // Funções que extraem os valores para a tabela
        List<Function<SpecificationElement<?>, String>> valueExtractors = List.of(
            element -> element.templateString(),
            element -> element.checkVisibility() ? "V" : "X", // Visibilidade
            element -> element.checkModifiers() ? "V" : "X",
            element -> element.checkParameters() ? "V" : "X", // Parâmetros
            element -> String.format("%.2f", element.getObtainedGrade()) + " / " + String.format("%.2f", element.getGrade())
        );

        // Chama o método da classe PdfTableService para gerar a tabela
        PdfTableService.addTable(document, headers, elements, valueExtractors);
    }

    // Adiciona tabela para os atributos (fields)
    private static void addTableForFields(Document document, List<SpecificationElement<?>> elements) {
        List<String> headers = List.of("Atributo", "Visibilidade", "Modificador", "Tipo", "Nota");

        // Funções que extraem os valores para a tabela
        List<Function<SpecificationElement<?>, String>> valueExtractors = List.of(
            element -> element.templateString(),
            element -> element.checkVisibility() ? "V" : "X", // Visibilidade
            element -> element.checkModifiers() ? "V" : "X", // Modificador
            element -> element.checkType() ? "V" : "X", // Tipo
            element -> String.format("%.2f", element.getObtainedGrade()) + " / " + String.format("%.2f", element.getGrade())
        );

        // Chama o método da classe PdfTableService para gerar a tabela
        PdfTableService.addTable(document, headers, elements, valueExtractors);
    }

    // Adiciona tabela para os métodos
    private static void addTableForMethods(Document document, List<SpecificationElement<?>> elements) {
        List<String> headers = List.of("Metodo", "Visibilidade", "Modificador", "Retorno", "Parâmetros", "Teste", "Nota");
        // List<String> headers = List.of("Metodo", "Visibilidade", "Modificador", "Retorno", "Parâmetros", "Nota");

        // Funções que extraem os valores para a tabela
        List<Function<SpecificationElement<?>, String>> valueExtractors = List.of(
            element -> element.templateString(),
            element -> element.checkVisibility() ? "V" : "X", // Visibilidade
            element -> element.checkModifiers() ? "V" : "X", // Modificador
            element -> element.checkReturnType() ? "V" : "X", // Retorno
            element -> element.checkParameters() ? "V" : "X", // Parâmetros
            element -> element.checkTest() ? "V" : "X",
            element -> String.format("%.2f", element.getObtainedGrade()) + " / " + String.format("%.2f", element.getGrade())
        );

        // Chama o método da classe PdfTableService para gerar a tabela
        PdfTableService.addTable(document, headers, elements, valueExtractors);
    }
}
