package com.university.parser;

import com.university.model.criteria.AverageAboveValue;
import com.university.model.criteria.Criteria;
import com.university.model.criteria.MaxAboveValue;
import com.university.model.criteria.MinAboveValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CriteriaParser implements Parser<Criteria> {

    /**
     * Reads and parses the criteria CSV file, adding the corresponding Criteria objects to the provided list.
     * @param filePath The path to the CSV file.
     * @param criteriaList List to hold the parsed criteria.
     */
    public void parse(String filePath, List<Criteria> criteriaList) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line in the CSV
                parseLine(line, criteriaList);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    /**
     * Parses a single line from the CSV file and adds the corresponding Criteria to the list if valid.
     * @param line A line from the CSV file.
     * @param criteriaList List to hold the parsed criteria.
     */
    private void parseLine(String line, List<Criteria> criteriaList) {
        // Trim the line to handle any extra spaces or blank lines
        line = line.trim();

        // Skip empty lines
        if (line.isEmpty()) {
            return;
        }

        String[] parts = line.split(",");

        // Ensure that there are enough parts to proceed
        if (parts.length < 4) { // Minimum parts needed: subject, exam type, threshold, at least one evaluation name
            System.out.println("Skipping invalid line: " + line); // Log the invalid line for debugging
            return;
        }

        // Extract fields and handle potential out-of-bound issues safely
        String subject = parts[0].trim();
        String examType = parts[1].trim();
        double threshold;

        try {
            threshold = Double.parseDouble(parts[2].trim()); // Parse threshold
        } catch (NumberFormatException e) {
            System.out.println("Invalid threshold in line: " + line);
            return; // Skip line if threshold is not a valid double
        }

        String[] evaluationNames = Arrays.copyOfRange(parts, 3, parts.length); // Get evaluation names

        // Create and add the corresponding Criteria based on the examType
        Criteria criteria = createCriteria(examType, subject, threshold, evaluationNames);
        criteriaList.add(criteria);
    }

    private Criteria createCriteria(String examType, String subject, double threshold, String[] evaluationNames) {
        switch (examType) {
            case "MAX_ABOVE_VALUE":
                return new MaxAboveValue(subject, threshold, evaluationNames);
            case "MIN_ABOVE_VALUE":
                return new MinAboveValue(subject, threshold, evaluationNames);
            case "AVERAGE_ABOVE_VALUE":
                return new AverageAboveValue(subject, threshold, evaluationNames);
            default:
                throw new IllegalArgumentException("Unknown exam type: " + examType);
        }
    }

}
