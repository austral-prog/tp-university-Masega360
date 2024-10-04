package com.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    private String inputFilePath;
    private String outputFilePath;

    @BeforeEach
    void setUp() throws IOException {
        // Set up paths for input and output files
        inputFilePath = "input.csv";
        outputFilePath = "output.csv";

        // Create a sample input CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFilePath))) {
            writer.write("Course_ID,Course_Name,Student_Name");
            writer.newLine();
            writer.write("1,Mathematics,Alice");
            writer.newLine();
            writer.write("2,Physics,Alice");
            writer.newLine();
            writer.write("3,Chemistry,Bob");
            writer.newLine();
            writer.write("4,Mathematics,Bob");
            writer.newLine();
            writer.write("5,Biology,Alice");
            writer.newLine();
        }
    }

    @Test
    void testGenerateParsedCSV() throws IOException {
        // Generate the output CSV
        App.generateParsedCSV(inputFilePath, outputFilePath);

        // Read the generated output CSV and verify the results
        Path outputPath = Paths.get(outputFilePath);
        assertTrue(Files.exists(outputPath), "Output file should exist.");

        try (BufferedReader br = new BufferedReader(new FileReader(outputFilePath))) {
            String header = br.readLine();
            assertEquals("Student_Name,Subject_Count", header, "Header should match.");

            // Read the lines and verify counts
            String line;
            int aliceCount = 0;
            int bobCount = 0;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if ("Alice".equals(values[0].trim())) {
                    aliceCount = Integer.parseInt(values[1].trim());
                } else if ("Bob".equals(values[0].trim())) {
                    bobCount = Integer.parseInt(values[1].trim());
                }
            }

            // Verify the expected counts
            assertEquals(3, aliceCount, "Alice should have 3 subjects.");
            assertEquals(2, bobCount, "Bob should have 2 subjects.");
        }
    }

    @Test
    void testInvalidInput() {
        // Test with a non-existing input file
        assertThrows(IOException.class, () -> {
            App.generateParsedCSV("non_existing_input.csv", outputFilePath);
        });
    }

    @Test
    void testEmptyInputFile() throws IOException {
        // Create an empty input file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFilePath))) {
            writer.write("Course_ID,Course_Name,Student_Name");
            writer.newLine();
        }

        // Generate the output CSV
        App.generateParsedCSV(inputFilePath, outputFilePath);

        // Verify that the output file has only the header
        Path outputPath = Paths.get(outputFilePath);
        assertTrue(Files.exists(outputPath), "Output file should exist.");

        try (BufferedReader br = new BufferedReader(new FileReader(outputFilePath))) {
            String header = br.readLine();
            assertEquals("Student_Name,Subject_Count", header, "Header should match.");
            assertNull(br.readLine(), "Output file should not contain any student data.");
        }
    }
}
