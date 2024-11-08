package com.university.service;

import com.university.model.student.Student;
import com.university.parser.StudentParser;
import com.university.writer.CSVWriter;
import com.university.service.sorters.GenericSorter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class CourseServiceTest {

    private CourseService courseService;

    @BeforeEach
    void setUp() {
        // Initialize CourseService before each test
        courseService = new CourseService();
    }

    @Test
    void testLoadAndParseStudents() {
        // Given: A predefined CSV file or mock data
        courseService.loadAndParseStudents(); // Load students from the CSV file

        // When: Students are loaded
        List<Student> students = CourseService.getStudents();

        // Then: Verify that the students list is not empty after loading
        assertFalse(students.isEmpty(), "Students list should not be empty after parsing");
        // Optionally, you can verify specific student details if needed, e.g., student name or count
    }

    @Test
    void testSortStudents() {
        // Given: Students are loaded into the list
        courseService.loadAndParseStudents();

        // Act: Sort the students by name
        courseService.sortStudents();

        // When: Students are sorted
        List<Student> students = CourseService.getStudents();

        // Then: Verify that students are sorted by name
        assertNotNull(students, "Students list should not be null");
        assertTrue(students.size() > 1, "There should be more than one student to verify sorting");

        // Verify that the students are sorted by name (ascending)
        for (int i = 0; i < students.size() - 1; i++) {
            assertTrue(students.get(i).getName().compareTo(students.get(i + 1).getName()) <= 0,
                    "Students should be sorted by name in ascending order");
        }
    }

    @Test
    void testWriteStudentReport() {
        // Given: Students are loaded and sorted
        courseService.loadAndParseStudents();
        courseService.sortStudents();

        // Act: Write the student report
        // Here, we would check if CSVWriter was called, but for this test we'll focus on file output.
        assertDoesNotThrow(() -> courseService.writeStudentReport(), "Writing student report should not throw exceptions");
    }
}
