package com.university.taskcoursetest.courselogic;

import com.university.model.student.Student;
import com.university.service.sorters.GenericSorter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

public class GenericSorterTest {

    @Test
    public void testSortByName() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", List.of("Math", "Science")));
        students.add(new Student("Bob", List.of("Math")));
        students.add(new Student("Charlie", List.of("History", "Math", "Science")));

        // Sort by name (use "getName" constructor name)
        GenericSorter.sort(students, "getName");

        // Assert the students are sorted by name
        assertEquals("Alice", students.get(0).getName());
        assertEquals("Bob", students.get(1).getName());
        assertEquals("Charlie", students.get(2).getName());
    }

    @Test
    public void testSortByCourseCount() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", List.of("Math", "Science")));
        students.add(new Student("Bob", List.of("Math")));
        students.add(new Student("Charlie", List.of("History", "Math", "Science")));

        // Sort by course count (use "getCourseCount" constructor name)
        GenericSorter.sort(students, "getCourseCount");

        // Assert the students are sorted by course count
        assertEquals(1, students.get(0).getCourseCount()); // Bob
        assertEquals(2, students.get(1).getCourseCount()); // Alice
        assertEquals(3, students.get(2).getCourseCount()); // Charlie
    }

    @Test
    public void testSortByMultipleCriteria() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", List.of("Math", "Science")));
        students.add(new Student("Bob", List.of("Math")));
        students.add(new Student("Charlie", List.of("Math", "Science", "History")));

        // Sort by course count first, then by name (use "getCourseCount" and "getName" constructor names)
        GenericSorter.sort(students, "getCourseCount", "getName");

        // Assert the students are sorted by course count and name
        assertEquals("Bob", students.get(0).getName());
        assertEquals("Alice", students.get(1).getName());
        assertEquals("Charlie", students.get(2).getName());
    }

    @Test
    public void testSortEmptyList() {
        List<Student> students = new ArrayList<>();

        // Sort on an empty list
        GenericSorter.sort(students, "getName");

        // Assert the list is still empty
        assertTrue(students.isEmpty());
    }

    @Test
    public void testSortWithNullValues() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", List.of("Math")));
        students.add(new Student("Bob", List.of("Math")));
        students.add(new Student("Charlie", null));  // Null courses list

        // Sort by course count, where null list should be handled
        GenericSorter.sort(students, "getCourseCount");

        // Assert that the student with the null courses list comes last
        assertEquals("Charlie", students.get(2).getName());
    }
}
