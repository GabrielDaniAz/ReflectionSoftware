package com.reflectionsoftware.model.result.reflection.comparison.specification;

public class ComparisonConstructor {
    private final String constructorName;
    private final boolean exists;
    private final boolean isVisibilityCorrect;
    private final boolean isModifierCorrect;
    private final boolean isParamTypesCorrect;

    private final String expectedVisibility;
    private final String actualVisibility;

    private final String expectedModifiers;
    private final String actualModifiers;

    private final String expectedParamTypes;
    private final String actualParamTypes;

    private final int grade;

    public ComparisonConstructor(
        String constructorName,
        boolean exists,
        boolean isVisibilityCorrect,
        boolean isModifierCorrect,
        boolean isParamTypesCorrect,
        String expectedVisibility,
        String actualVisibility,
        String expectedModifiers,
        String actualModifiers,
        String expectedParamTypes,
        String actualParamTypes,
        int grade
    ) {
        this.constructorName = constructorName;
        this.exists = exists;
        this.isVisibilityCorrect = isVisibilityCorrect;
        this.isModifierCorrect = isModifierCorrect;
        this.isParamTypesCorrect = isParamTypesCorrect;
        this.expectedVisibility = expectedVisibility;
        this.actualVisibility = actualVisibility;
        this.expectedModifiers = expectedModifiers;
        this.actualModifiers = actualModifiers;
        this.expectedParamTypes = expectedParamTypes;
        this.actualParamTypes = actualParamTypes;
        this.grade = grade;
    }

    public String getConstructorName() { return constructorName; }
    public boolean exists() { return exists; }
    public boolean isVisibilityCorrect() { return isVisibilityCorrect; }
    public boolean isModifierCorrect() { return isModifierCorrect; }
    public boolean isParamTypesCorrect() { return isParamTypesCorrect; }
    public String getExpectedVisibility() { return expectedVisibility; }
    public String getActualVisibility() { return actualVisibility; }
    public String getExpectedModifiers() { return expectedModifiers;  }
    public String getActualModifiers() { return actualModifiers; }
    public String getExpectedParamTypes() { return expectedParamTypes; }
    public String getActualParamTypes() { return actualParamTypes; }
    public int getGrade() { return grade; }
    public boolean hasErrors() { return !isVisibilityCorrect || !isModifierCorrect || !isParamTypesCorrect; }

    public String getErrors() {
        StringBuilder errors = new StringBuilder("Erros no construtor com parâmetros (" + expectedParamTypes + "):\n");

        if (!isVisibilityCorrect) { errors.append(" - Visibilidade incorreta: esperado '")
                .append(expectedVisibility).append("', mas foi '").append(actualVisibility).append("'.\n"); }

        if (!isModifierCorrect) { errors.append(" - Modificadores incorretos: esperado '")
                .append(expectedModifiers).append("', mas foi '").append(actualModifiers).append("'.\n"); }

        return errors.length() > 0 ? errors.toString() : "Nenhum erro encontrado.";
    }

    public String getCorrects() {
        StringBuilder corrects = new StringBuilder("Acertos no construtor com parâmetros (" + expectedParamTypes + "):\n");

        if (isVisibilityCorrect) { corrects.append(" - Visibilidade correta: '").append(actualVisibility).append("'.\n"); }
        if (isModifierCorrect) {  corrects.append(" - Modificadores corretos: '").append(actualModifiers).append("'.\n"); }

        return corrects.length() > 0 ? corrects.toString() : "Nenhum acerto encontrado.";
    }

    public String getExpected() {
        StringBuilder expected = new StringBuilder();
        expected.append(expectedVisibility).append(" ")
                .append(expectedModifiers).append(" ")
                .append(constructorName).append("(").append(expectedParamTypes).append(")");

        return expected.toString();
    }

    public String getActual() {
        StringBuilder actual = new StringBuilder();
        actual.append(actualVisibility).append(" ")
                .append(actualModifiers).append(" ")
                .append(constructorName).append("(").append(actualParamTypes).append(")");

        return actual.toString();
    }

    @Override
    public String toString() {
        StringBuilder correction = new StringBuilder("Correção para o construtor com parâmetros (" + expectedParamTypes + "):\n");

        if (!isVisibilityCorrect) {
            correction.append(" - Visibilidade incorreta: esperado '")
                      .append(expectedVisibility)
                      .append("', mas foi '")
                      .append(actualVisibility)
                      .append("'.\n");
        } else {
            correction.append(" - Visibilidade correta.\n");
        }

        if (!isModifierCorrect) {
            correction.append(" - Modificadores incorretos: esperado '")
                      .append(expectedModifiers)
                      .append("', mas foi '")
                      .append(actualModifiers)
                      .append("'.\n");
        } else {
            correction.append(" - Modificadores corretos.\n");
        }

        return correction.toString();
    }
}
