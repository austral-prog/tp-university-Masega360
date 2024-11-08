package com.university.model.cli.repository;



import com.university.model.cli.CLIImpl;
import com.university.model.student.Student;
import com.university.service.CourseService;
import com.university.writer.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentRepository implements CRUDRepository<Student> {
    private static final Map<Integer, Student> studentStorage = new HashMap<>();
    private static int idn = 1; // Initialize id to 1 at the start

    public static void StudentRepositoryInitializer() {
        CourseService courseService = new CourseService();
        courseService.loadAndParseStudents();
        courseService.sortStudents();
        // Populate students from the StudentService
        List<Student> students = CourseService.getStudents();
        for (Student student : students) {
            student.setId(idn); // Assign the current id
            studentStorage.put(student.getId(), student); // Store in map
            idn++; // Increment id by 1 for the next iteration
        }
    }

    @Override
    public void create(Student student) {
        student.setId(idn);
        idn++;
        studentStorage.put(student.getId(), student);
        System.out.println("Created:" + student.toParsedString());
    }

    @Override
    public Student read(int id) {
        try {
            return studentStorage.get(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void update(int id, Student student) {
        Student prevStudent = studentStorage.get(id);
        System.out.println("Updating:" + prevStudent.toParsedString());
        if (studentStorage.containsKey(id)) {
            studentStorage.put(id, student);
            System.out.println("Updated to:" + student.toParsedString());
        }
    }

    @Override
    public void delete(int id) {
        System.out.println("Deleting:" + studentStorage.get(id).toParsedString());
        studentStorage.remove(id);
    }

    @Override
    public String getIdentifier() {
        return "Student";
    }

    @Override
    public Class<Student> getEntityClass() {
        return Student.class;
    }
    public String getUserInput(String prompt) {
        String result;
        result = CLIImpl.userInput(prompt);
        return result;
    }

    public List<String> inputs(){
        List<String> result = new ArrayList<>();
        String sutdname = CLIImpl.userInput("Full Name");
        String studCourses = CLIImpl.userInput("Subject (List all subjects in this format: subject1,subject2,...)");
        result.add(sutdname);
        result.add(studCourses);
        return result;
    }

    @Override
    public Student constructFromList() {
        List<String> inputs = inputs();
        if (inputs.size() < 2) {
            System.out.println("Insufficient data provided to create Evaluation.");
            return null;
        }

        String studentFullName = inputs.get(0);
        String subjecte = inputs.get(1);
        List<String> subjects = new ArrayList<>();

        for (String subject : subjecte.split(",")) {
            subjects.add(subject);
        }
        Student student = new Student(studentFullName, subjects);
        return student;


    }

    public void showByStudentName(String studentName) {
        // Use a Map to store the student details by their ID
        Map<Integer, String> studentDetails = new HashMap<>();

        for (Student student : studentStorage.values()) {
            if (student.getName().equals(studentName)) {
                // Store the student object in the map with its ID as the key and the parsed string as the value
                studentDetails.put(student.getId(), student.toParsedString());
            }
        }

        // Print the student details
        if (studentDetails.isEmpty()) {
            System.out.println("No student found with name: " + studentName);
        } else {
            System.out.println("Student details for " + studentName + ":");
            studentDetails.forEach((id, detail) -> {
                System.out.println("ID: " + id + " -> " + detail);
            });
        }
    }


    public List<Student> mapToList(){
        List<Student> result = new ArrayList<>();
        for (Student student : studentStorage.values()) {
            result.add(student);
        }
        return result;
    }
    public void writeCSV() {
        String filePath = "src/main/resources/reports/CLIsolution.csv";
        CSVWriter writer = new CSVWriter();
        String Header = "Student_Name,Course_Count";
        writer.writeToCSV(filePath, mapToList(), Header);

    }

}
