package com.reflectionsoftware.util.reflection;

public class NameSimilarityCalculator {

    /**
     * Calcula a similaridade entre dois nomes (valores entre 0 e 1).
     */
    public static double calculate(String name1, String name2) {
        int maxLength = Math.max(name1.length(), name2.length());
        if (maxLength == 0) return 1.0; // Ambos vazios são idênticos.

        int distance = calculateLevenshteinDistance(name1.toLowerCase(), name2.toLowerCase());
        return 1.0 - (double) distance / maxLength;
    }

    /**
     * Implementação da distância de Levenshtein.
     */
    private static int calculateLevenshteinDistance(String s1, String s2) {
        int[] prevRow = new int[s2.length() + 1];
        int[] currentRow = new int[s2.length() + 1];

        for (int j = 0; j <= s2.length(); j++) prevRow[j] = j;

        for (int i = 1; i <= s1.length(); i++) {
            currentRow[0] = i;
            for (int j = 1; j <= s2.length(); j++) {
                int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                currentRow[j] = Math.min(
                        Math.min(currentRow[j - 1] + 1, prevRow[j] + 1),
                        prevRow[j - 1] + cost
                );
            }
            System.arraycopy(currentRow, 0, prevRow, 0, currentRow.length);
        }
        return prevRow[s2.length()];
    }
}
