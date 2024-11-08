package com.university.taskcoursetest.repository;

import com.university.model.cli.repository.StudentRepository;
import com.university.model.student.Student;
import com.university.writer.CSVWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentRepositoryTest {

    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository = new StudentRepository();
    }

    @Test
    void testCreateAndReadStudent() {
        // Create a new student and add it to the repository
        Student student = new Student("John Doe", Arrays.asList("Math", "Science"));
        studentRepository.create(student);

        // Read the student from the repository
        Student retrievedStudent = studentRepository.read(student.getId());

        // Verify the student data matches
        assertNotNull(retrievedStudent, "Student should be found");
        assertEquals("John Doe", retrievedStudent.getName());
        assertEquals(Arrays.asList("Math", "Science"), retrievedStudent.getCourses());
    }

    @Test
    void testDeleteStudent() {
        // Create and add a student
        Student student = new Student("Alice", Arrays.asList("Art"));
        studentRepository.create(student);

        // Delete the student
        studentRepository.delete(student.getId());

        // Verify the student was deleted
        assertNull(studentRepository.read(student.getId()), "Student should be deleted");
    }


    @Test
    void testWriteCSV() {
        // Create and add students to the repository
        Student student = new Student("David", Arrays.asList("Biology"));
        studentRepository.create(student);

        // Call writeCSV method
        studentRepository.writeCSV();

        // Verify the CSV file exists and has the correct data
        File csvFile = new File("src/main/resources/reports/CLIsolution.csv");
        assertTrue(csvFile.exists(), "CSV file should be created");

        // Clean up the CSV file after the test if needed
        csvFile.delete();
    }
}
