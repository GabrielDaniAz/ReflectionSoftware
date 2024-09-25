package controller;

import java.util.ArrayList;
import java.util.List;

import model.Report;

// Gerencia a criação de relatórios para os alunos, utilizando o serviço de geração de PDFs.
public class ReportManager {
    
    private static List<Report> reports = new ArrayList<>();
    
    public static void addReport(Report report){
        reports.add(report);
    }

    public static void generateDetails(){
        for (Report report : reports) {
            System.out.println(report.generateReportDetails());
        }
    }
}
