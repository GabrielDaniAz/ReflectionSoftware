package model;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private String studentName;
    private List<FileResult> fileResults;

    public Report(String studentName) {
        this.studentName = studentName;
        this.fileResults = new ArrayList<>();
    }

    public List<FileResult> getFileResults() {
        return List.copyOf(fileResults);
    }

    public void addFileResult(FileResult fileResult) {
        if (fileResult != null) {
            fileResults.add(fileResult);
        } else {
            throw new IllegalArgumentException("FileResult não deve ser nulo.");
        }
    }

    /**
     * Gera os detalhes completos do relatório, incluindo os resultados de compilação e reflexão de cada arquivo .java.
     * 
     * @return String contendo os detalhes completos do relatório.
     */
    public String generateReportDetails() {

        String reportString = String.format("""
        ############################################
        Relatório do Aluno %s possui %d arquivo(s)
        
        """
        , studentName, fileResults.size());
            
        // Iterar sobre os FileResult e incluir os detalhes de compilação e reflexão
        int i = 0;
        for (FileResult fileResult : fileResults) {
            i++;
            reportString += String.format("""
            %s
            """
            , fileResult.generateFileDetails(i));

        }
        reportString += "############################################\n";

        return reportString;
    }
}
