package com.university.taskcoursetest.courselogic;

import com.university.parser.StudentParser;
import com.university.model.student.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentParserTest {

    private static final String FILE_PATH = "testresources/testinput.csv";

    @BeforeEach
    public void setUp() throws IOException {
        // Try to load the file from the classpath
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_PATH);

        if (inputStream == null) {
            throw new IOException("File not found: " + FILE_PATH);
        }

        // Close the stream to ensure resources are freed
        inputStream.close();
    }


    @Test
    public void testParse() {
        StudentParser parser = new StudentParser();
        List<Student> students = new ArrayList<>();

        // Parse the file into the students list
        parser.parse(FILE_PATH, students);

        // Validate that the list is not empty (i.e., the file was successfully parsed)
        assertFalse(students.isEmpty(), "The student list should not be empty.");

        // Check the expected number of students
        assertEquals(3, students.size(), "Expected 3 students to be parsed");

        // Verify specific student data
        Student firstStudent = students.get(0);
        assertEquals("Olivia Red", firstStudent.getName(), "First student's name should be Olivia Red");
        assertTrue(firstStudent.getCourses().contains("Political Science"), "First student should be enrolled in Political Science");

        Student secondStudent = students.get(1);
        assertEquals("Quincy Johnson", secondStudent.getName(), "Second student's name should be Quincy Johnson");
        assertTrue(secondStudent.getCourses().contains("Mathematics"), "Second student should be enrolled in Mathematics");

        // Check if a student appears only once
        assertEquals(1, students.stream().filter(s -> s.getName().equals("Olivia Red")).count(), "There should be only one Olivia Red");
    }

    @Test
    public void testValidateRecord() {
        StudentParser parser = new StudentParser();

        // Test a valid record
        String[] validRecord = {"1", "Math", "Alice"};
        assertTrue(parser.validateRecord(validRecord), "Valid record should return true.");

        // Test an invalid record (empty array)
        String[] invalidRecord = {};
        assertFalse(parser.validateRecord(invalidRecord), "Empty record should return false.");

        // Test an invalid record (null)
        String[] nullRecord = null;
        assertFalse(parser.validateRecord(nullRecord), "Null record should return false.");
    }
}
