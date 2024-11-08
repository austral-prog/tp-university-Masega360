package com.university.taskcoursetest.courselogic;

import com.university.model.student.Student;
import com.university.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

public class ParserTest {

    // A mock implementation of the Parser interface to test the logic
    public class MockStudentParser implements Parser<Student> {

        @Override
        public void parse(String filePath, List<Student> parsedRecords) {
            // Simulate parsing logic (for testing purposes)
            String[] mockRecord = {"Alice", "Math,Science"};
            if (validateRecord(mockRecord)) {
                Student student = new Student(mockRecord[0], List.of(mockRecord[1].split(",")));
                parsedRecords.add(student);
            }
        }
    }

    @Test
    public void testValidateRecord() {
        Parser<Student> parser = new MockStudentParser();

        // Test a valid record
        String[] validRecord = {"Alice", "Math,Science"};
        assertTrue(parser.validateRecord(validRecord));

        // Test an invalid record (empty)
        String[] invalidRecord = {};
        assertFalse(parser.validateRecord(invalidRecord));

        // Test an invalid record (null)
        assertFalse(parser.validateRecord(null));
    }

    @Test
    public void testParse() {
        Parser<Student> parser = new MockStudentParser();
        List<Student> students = new ArrayList<>();

        // Simulate parsing a file (filePath is irrelevant for this mock)
        parser.parse("dummy/path/to/file", students);

        // Assert that the list of students is populated correctly
        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
        assertEquals("Alice", students.get(0).getName());
        assertEquals(2, students.get(0).getCourseCount());
    }
}
