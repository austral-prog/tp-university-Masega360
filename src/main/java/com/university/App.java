package com.university;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        String inputFilePath = "input.csv"; // Path to the input CSV file
        String outputFilePath = "solution.csv"; // Path to the output CSV file

        try {
            generateParsedCSV(inputFilePath, outputFilePath);
            System.out.println("Parsed CSV generated successfully: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error processing CSV files: " + e.getMessage());
        }
    }

    public static void generateParsedCSV(String inputFilePath, String outputFilePath) throws IOException {
        Map<String, Integer> studentCourseCount = new HashMap<>();

        // Read the input CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            // Skip the header
            br.readLine();

            // Process each line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    String studentName = values[2].trim(); // Get student name
                    studentCourseCount.put(studentName, studentCourseCount.getOrDefault(studentName, 0) + 1);
                }
            }
        }

        // Write to the output CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write("Student_Name,Subject_Count"); // Write header
            writer.newLine();

            for (Map.Entry<String, Integer> entry : studentCourseCount.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue()); // Write student name and subject count
                writer.newLine();
            }
        }
    }
}
