package com.university.parser;

import com.university.model.student.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentParser implements Parser<Student> {

    @Override
    public void parse(String filePath, List<Student> students) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath))))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (validateRecord(parts)) {
                    String studentName = parts[2].trim();
                    String course = parts[1].trim();

                    // Check if student already exists in the provided list
                    Student existingStudent = students.stream()
                            .filter(s -> s.getName().equals(studentName))
                            .findFirst()
                            .orElse(null);

                    if (existingStudent != null) {
                        // If student exists, add the course to their list
                        existingStudent.addCourse(course);
                    } else {
                        // If student doesn't exist, create a new one with the course
                        List<String> courses = new ArrayList<>(List.of(course));
                        Student student = new Student(studentName, courses);
                        students.add(student);  // Add new student to the list
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
