package controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import factory.StudentFactory;
import model.Student;
import service.CorrectionService;

// Controla o fluxo de correção, orquestrando os serviços necessários para buscar os arquivos dos alunos, instanciar objetos Student, e iniciar a correção.
public class CorrectionController {
    
    /**
     * Inicia o processo de correção a partir do caminho do diretório.
     * 
     * @param directoryPath Caminho do diretório onde estão os arquivos dos alunos.
     * @throws Exception 
     */
    public static void processCorrections(String directoryPath) throws Exception {
        // 1. Obter arquivos .java dos alunos
        Map<String, List<File>> javaFilesByStudent = FileController.getJavaFilesByStudent(directoryPath);

        // 2. Criar objetos Student
        List<Student> students = StudentFactory.createStudents(javaFilesByStudent);

        // 3. Iniciar a correção para cada aluno
        for (Student student : students) {
            CorrectionService.correctStudent(student);
        }

        // 4. Gerar os relatórios finais para todos os alunos
        ReportManager.generateDetails();
    }
}
