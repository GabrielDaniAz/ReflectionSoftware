package com.reflectionsoftware.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.reflectionsoftware.model.Student;

// Responsável por criar objetos Student a partir de um mapa de arquivos.
public class StudentController {

    private List<Student> students;

    public StudentController(Map<File, List<File>> studentFilesMap) {
        this.students = createStudents(studentFilesMap);
    }

    /**
     * Cria uma lista de objetos Student a partir de um mapa contendo o nome do aluno e seus arquivos.
     * 
     * @param studentFilesMap Mapa com o nome do aluno (chave) e seus arquivos .java (valor).
     * @return Lista de objetos Student.
     */
    private List<Student> createStudents(Map<File, List<File>> studentFilesMap){
        List<Student> students = new ArrayList<>();

        for(Map.Entry<File, List<File>> entry : studentFilesMap.entrySet()){
            File studentDirectory = entry.getKey();
            List<File> submittedFiles = entry.getValue();
            students.add(new Student(studentDirectory, submittedFiles));
        }

        return students;
    }

    /**
     * Retorna uma lista contendo todos os Student.
     * 
     * @return Mapa de subdiretórios e arquivos .java.
     */
    public List<Student> getStudents() {
        return students;
    }
}
