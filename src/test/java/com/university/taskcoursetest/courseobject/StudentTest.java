package com.university.taskcoursetest.courseobject;

import com.university.model.student.Student;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class StudentTest {

    @Test
    public void testStudentConstructor() {
        // Test the constructor
        List<String> courses = List.of("Math", "Science");
        Student student = new Student("Alice", courses);

        // Assert the name and course list are set correctly
        assertEquals("Alice", student.getName());
        assertEquals(2, student.getCourseCount());
    }

    @Test
    public void testAddCourse() {
        // Test adding a new course
        List<String> courses = new ArrayList<>(List.of("Math")); // Use ArrayList for mutability
        Student student = new Student("Alice", courses);

        student.addCourse("Science");
        assertTrue(student.getCourseCount() == 2); // After adding "Science", count should be 2
        assertTrue(student.toParsedString().contains("2")); // Check if the course count is now 2

        // Test adding a duplicate course (should not add)
        student.addCourse("Math");
        assertTrue(student.getCourseCount() == 2); // Should still be 2 after trying to add "Math" again
    }


    @Test
    public void testToParsedString() {
        // Test the toParsedString method
        List<String> courses = List.of("Math", "Science");
        Student student = new Student("Alice", courses);

        // Ensure the string is in the format "Name,CourseCount"
        assertEquals("Alice,2", student.toParsedString());
    }

    @Test
    public void testGetName() {
        // Test the getName method
        List<String> courses = List.of("Math", "Science");
        Student student = new Student("Alice", courses);

        assertEquals("Alice", student.getName());
    }
}
