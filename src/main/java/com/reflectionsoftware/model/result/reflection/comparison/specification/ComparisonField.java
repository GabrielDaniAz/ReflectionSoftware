package com.reflectionsoftware.model.result.reflection.comparison.specification;

public class ComparisonField {
    private final String fieldName;
    private final boolean exists;
    private final boolean isVisibilityCorrect;
    private final boolean isModifierCorrect;
    private final boolean isTypeCorrect;

    private final String expectedVisibility;
    private final String actualVisibility;

    private final String expectedModifiers;
    private final String actualModifiers;

    private final String expectedType;
    private final String actualType;

    private final int grade;

    public ComparisonField(
        String fieldName,
        boolean exists,
        boolean isVisibilityCorrect,
        boolean isModifierCorrect,
        boolean isTypeCorrect,
        String expectedVisibility,
        String actualVisibility,
        String expectedModifiers,
        String actualModifiers,
        String expectedType,
        String actualType,
        int grade
    ) {
        this.fieldName = fieldName;
        this.exists = exists;
        this.isVisibilityCorrect = isVisibilityCorrect;
        this.isModifierCorrect = isModifierCorrect;
        this.isTypeCorrect = isTypeCorrect;
        this.expectedVisibility = expectedVisibility;
        this.actualVisibility = actualVisibility;
        this.expectedModifiers = expectedModifiers;
        this.actualModifiers = actualModifiers;
        this.expectedType = expectedType;
        this.actualType = actualType;
        this.grade = grade;
    }

    public String getFieldName() { return fieldName; }
    public boolean exists() { return exists; }
    public boolean isVisibilityCorrect() { return isVisibilityCorrect; }
    public boolean isModifierCorrect() { return isModifierCorrect; }
    public boolean isTypeCorrect() { return isTypeCorrect; }
    public String getExpectedVisibility() { return expectedVisibility; }
    public String getActualVisibility() { return actualVisibility; }
    public String getExpectedModifiers() { return expectedModifiers; }
    public String getActualModifiers() { return actualModifiers; }
    public String getExpectedType() { return expectedType; }
    public String getActualType() { return actualType; }
    public int getGrade() { return grade; }

    public String getErrors() {
        StringBuilder errors = new StringBuilder("Erros no campo '" + fieldName + "':\n");
        if (!isVisibilityCorrect) errors.append(" - Visibilidade incorreta: esperado '")
                .append(expectedVisibility).append("', mas foi '").append(actualVisibility).append("'.\n");
        if (!isModifierCorrect) errors.append(" - Modificadores incorretos: esperado '")
                .append(expectedModifiers).append("', mas foi '").append(actualModifiers).append("'.\n");
        if (!isTypeCorrect) errors.append(" - Tipo incorreto: esperado '")
                .append(expectedType).append("', mas foi '").append(actualType).append("'.\n");
        return errors.toString();
    }

    public String getCorrects() {
        StringBuilder corrects = new StringBuilder("Acertos no campo '" + fieldName + "':\n");
        if (isVisibilityCorrect) corrects.append(" - Visibilidade correta: '").append(actualVisibility).append("'.\n");
        if (isModifierCorrect) corrects.append(" - Modificadores corretos: '").append(actualModifiers).append("'.\n");
        if (isTypeCorrect) corrects.append(" - Tipo correto: '").append(actualType).append("'.\n");
        return corrects.toString();
    }

    public String getExpected() {
        StringBuilder expected = new StringBuilder();
        expected.append(expectedVisibility).append(" ")
                .append(expectedModifiers).append(" ")
                .append(expectedType).append(" ")
                .append(fieldName);

        return expected.toString();
    }

    public String getActual() {
        StringBuilder actual = new StringBuilder();
        actual.append(actualVisibility).append(" ")
                .append(actualModifiers).append(" ")
                .append(actualType).append(" ")
                .append(fieldName);

        return actual.toString();
    }

    @Override
    public String toString() {
        return String.format(
            "Correção para o campo '%s':\n%s\n%sNota do campo: %d\n",
            fieldName,
            getErrors(),
            getCorrects(),
            grade
        );
    }
}
