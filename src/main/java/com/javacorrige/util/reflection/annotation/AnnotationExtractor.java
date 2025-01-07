package com.javacorrige.util.reflection.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public class AnnotationExtractor {

    /**
     * Retorna uma anotação de um elemento anotado, baseada no nome simples da anotação.
     * 
     * @param element o elemento que pode conter anotações (deve ser uma instância de AnnotatedElement)
     * @param annotationName o nome simples da anotação a ser procurada
     * @return a anotação correspondente ou {@code null} se não encontrada
     */
    private static Annotation getAnnotationByName(Object element, String annotationName) {
        if (element instanceof AnnotatedElement annotatedElement) {
            for (Annotation annotation : annotatedElement.getAnnotations()) {
                if (annotation.annotationType().getSimpleName().equals(annotationName)) {
                    return annotation;
                }
            }
        }
        return null;
    }

    /**
     * Obtém a anotação @Especificacao de um elemento, se existir.
     * 
     * @param element o elemento a ser analisado
     * @return a anotação @Especificacao ou {@code null} se não encontrada
     */
    public static Annotation getSpecification(Object element) {
        return getAnnotationByName(element, "Especificacao");
    }

    /**
     * Obtém a anotação @Nota de um elemento, se existir.
     * 
     * @param element o elemento a ser analisado
     * @return a anotação @Nota ou {@code null} se não encontrada
     */
    public static Annotation getGrade(Object element) {
        return getAnnotationByName(element, "Nota");
    }

    /**
     * Obtém a anotação @Testar de um elemento, se existir.
     * 
     * @param element o elemento a ser analisado
     * @return a anotação @Testar ou {@code null} se não encontrada
     */
    public static Annotation getTest(Object element) {
        return getAnnotationByName(element, "Testar");
    }
}
