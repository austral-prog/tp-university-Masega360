package com.university.service;

import com.university.model.student.Student;
import com.university.service.sorters.GenericSorter;
import com.university.writer.CSVWriter;
import com.university.parser.StudentParser;

import java.util.List;
import java.util.ArrayList;

public class CourseService {

    private static List<Student> students;

    public CourseService() {
        this.students = new ArrayList<>();  // Initialize students as an empty list
    }

    public void loadAndParseStudents() {
        StudentParser studentParser = new StudentParser();
        studentParser.parse("db/input.csv", students);  // Pass the empty list to the parser
    }

    public void sortStudents(){
        GenericSorter.sort(students, "getName");
    }



    public void writeStudentReport(){
        CSVWriter writer = new CSVWriter();
        String courseHeader = "Student_Name,Course_Count";
        writer.writeToCSV("src/main/resources/reports/solution.csv", students, courseHeader);  // Write the students to the report
    }

    public static List<Student> getStudents() {
        return students;
    }
}
