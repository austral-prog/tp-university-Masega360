package com.university.taskcoursetest.courselogic;

import com.university.model.student.Student;
import com.university.writer.CSVWriter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVWriterTest {

    @Test
    public void testWriteToCSV() throws IOException {
        // Prepare the student data
        List<Student> students = new ArrayList<>();

        // Create students with subjects
        students.add(new Student("John Doe", List.of("Math", "Science")));
        students.add(new Student("Jane Smith", List.of("English", "History")));

        // Prepare a header
        String header = "Name,Subjects";

        // Create a temporary file for testing
        File tempFile = File.createTempFile("students", ".csv");
        tempFile.deleteOnExit(); // Ensure the temp file is deleted after the test

        // Create the CSVWriter
        CSVWriter csvWriter = new CSVWriter();

        // Write the data to the temp file
        csvWriter.writeToCSV(tempFile.getAbsolutePath(), students, header);

        // Read the content of the file
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(tempFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }

        // Expected output as a CSV string
        String expectedOutput = "Name,Subjects\nJohn Doe,2\nJane Smith,2\n";

        // Compare the content of the file with the expected string
        assertEquals(expectedOutput, fileContent.toString());
    }
}
