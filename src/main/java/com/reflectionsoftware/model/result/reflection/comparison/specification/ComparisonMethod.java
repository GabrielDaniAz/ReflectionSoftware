package com.reflectionsoftware.model.result.reflection.comparison.specification;

public class ComparisonMethod {
    private final String methodName;
    private final boolean exists;
    private final boolean isVisibilityCorrect;
    private final boolean isModifierCorrect;
    private final boolean isReturnTypeCorrect;
    private final boolean isParamTypesCorrect;

    private final String expectedVisibility;
    private final String actualVisibility;

    private final String expectedModifiers;
    private final String actualModifiers;

    private final String expectedReturnType;
    private final String actualReturnType;

    private final String expectedParamTypes;
    private final String actualParamTypes;

    private final int grade;

    public ComparisonMethod(
        String methodName,
        boolean exists,
        boolean isVisibilityCorrect,
        boolean isModifierCorrect,
        boolean isReturnTypeCorrect,
        boolean isParamTypesCorrect,
        String expectedVisibility,
        String actualVisibility,
        String expectedModifiers,
        String actualModifiers,
        String expectedReturnType,
        String actualReturnType,
        String expectedParamTypes,
        String actualParamTypes,
        int grade
    ) {
        this.methodName = methodName;
        this.exists = exists;
        this.isVisibilityCorrect = isVisibilityCorrect;
        this.isModifierCorrect = isModifierCorrect;
        this.isReturnTypeCorrect = isReturnTypeCorrect;
        this.isParamTypesCorrect = isParamTypesCorrect;

        this.expectedVisibility = expectedVisibility;
        this.actualVisibility = actualVisibility;

        this.expectedModifiers = expectedModifiers;
        this.actualModifiers = actualModifiers;

        this.expectedReturnType = expectedReturnType;
        this.actualReturnType = actualReturnType;

        this.expectedParamTypes = expectedParamTypes;
        this.actualParamTypes = actualParamTypes;

        this.grade = grade;
    }

    public String getMethodName() { return methodName; }
    public boolean exists() { return exists; }
    public boolean isVisibilityCorrect() { return isVisibilityCorrect; }
    public boolean isModifierCorrect() { return isModifierCorrect; }
    public boolean isReturnTypeCorrect() { return isReturnTypeCorrect; }
    public boolean isParamTypesCorrect() { return isParamTypesCorrect; }
    public String getExpectedVisibility() { return expectedVisibility; }
    public String getActualVisibility() { return actualVisibility; }
    public String getExpectedModifiers() { return expectedModifiers;  }
    public String getActualModifiers() { return actualModifiers; }
    public String getExpectedReturnType() { return expectedReturnType; }
    public String getActualReturnType() { return actualReturnType; }
    public String getExpectedParamTypes() { return expectedParamTypes; }
    public String getActualParamTypes() { return actualParamTypes; }
    public int getGrade() { return grade; }
    public boolean hasErrors() { return !isVisibilityCorrect || !isModifierCorrect || !isReturnTypeCorrect || !isParamTypesCorrect; }

    public String getErrors() {
        StringBuilder errors = new StringBuilder("Erros no método '" + methodName + "':\n");

        if (!isVisibilityCorrect) { errors.append(" - Visibilidade incorreta: esperado '")
                .append(expectedVisibility).append("', mas foi '").append(actualVisibility).append("'.\n"); }

        if (!isModifierCorrect) {errors.append(" - Modificadores incorretos: esperado '")
                .append(expectedModifiers).append("', mas foi '").append(actualModifiers).append("'.\n"); }

        if (!isReturnTypeCorrect) { errors.append(" - Tipo de retorno incorreto: esperado '")
                .append(expectedReturnType).append("', mas foi '").append(actualReturnType).append("'.\n"); }

        return errors.length() > 0 ? errors.toString() : "Nenhum erro encontrado.";
    }

    public String getCorrects() {
        StringBuilder corrects = new StringBuilder("Acertos no método '" + methodName + "':\n");

        if (isVisibilityCorrect) { corrects.append(" - Visibilidade correta: '").append(actualVisibility).append("'.\n"); }
        if (isModifierCorrect) { corrects.append(" - Modificadores corretos: '").append(actualModifiers).append("'.\n"); }
        if (isReturnTypeCorrect) { corrects.append(" - Tipo de retorno correto: '").append(actualReturnType).append("'.\n"); }

        return corrects.length() > 0 ? corrects.toString() : "Nenhum acerto encontrado.";
    }

    public String getExpected() {
        StringBuilder expected = new StringBuilder();
        expected.append(expectedVisibility).append(" ")
                .append(expectedModifiers).append(" ")
                .append(expectedReturnType).append(" ")
                .append(methodName).append("(").append(expectedParamTypes).append(")");

        return expected.toString();
    }

    public String getActual() {
        StringBuilder actual = new StringBuilder();
        actual.append(actualVisibility).append(" ")
                .append(actualModifiers).append(" ")
                .append(actualReturnType).append(" ")
                .append(methodName).append("(").append(actualParamTypes).append(")");

        return actual.toString();
    }

    @Override
    public String toString() {
        StringBuilder correction = new StringBuilder("Correção para o método '" + methodName + "':\n");

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

        if (!isReturnTypeCorrect) {
            correction.append(" - Tipo de retorno incorreto: esperado '")
                      .append(expectedReturnType)
                      .append("', mas foi '")
                      .append(actualReturnType)
                      .append("'.\n");
        } else {
            correction.append(" - Tipo de retorno correto.\n");
        }

        correction.append(" - Parâmetros: ").append(expectedParamTypes).append("\n");

        return correction.toString();
    }
}