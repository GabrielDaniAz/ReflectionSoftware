package com.reflectionsoftware.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.reflectionsoftware.model.Student;
import com.reflectionsoftware.model.clazz.ClassInfo;

public class StudentManager {
    
    private final List<Student> students;

    public StudentManager(Map<String, List<ClassInfo>> studentMap){
        this.students = new ArrayList<>();
        for (Map.Entry<String, List<ClassInfo>> entry : studentMap.entrySet()) {
            String studentName = entry.getKey();
            if (studentName.contains(" - ")) {
                studentName = studentName.split(" - ")[0].trim();
            }        
            List<ClassInfo> classes = entry.getValue();
            students.add(new Student(studentName, classes));
        }
    }

    public List<Student> getStudents(){ return students; }
}
