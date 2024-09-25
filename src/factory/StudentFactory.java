package factory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Student;

// Responsável por criar objetos Student a partir de um mapa de arquivos.
public class StudentFactory {

    /**
     * Cria uma lista de objetos Student a partir de um mapa contendo o nome do aluno e seus arquivos.
     * 
     * @param studentFilesMap Mapa com o nome do aluno (chave) e seus arquivos .java (valor).
     * @return Lista de objetos Student.
     */
    public static List<Student> createStudents(Map<String, List<File>> studentFilesMap){
        List<Student> students = new ArrayList<>();

        for(Map.Entry<String, List<File>> entry : studentFilesMap.entrySet()){
            String studentName = entry.getKey();
            List<File> submittedFiles = entry.getValue();
            students.add(new Student(studentName, submittedFiles));
        }

        return students;
    }
}
