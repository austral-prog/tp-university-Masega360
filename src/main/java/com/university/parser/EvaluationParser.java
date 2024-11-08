package com.university.parser;

import com.university.model.evaluation.*;

import java.io.*;
import java.util.*;
import java.util.List;

public class EvaluationParser implements Parser<Evaluation> {

    @Override
    public void parse(String filePath, List<Evaluation> evaluations) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath))))) {

            // Skip header
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // Validate record using the default method
                if (validateRecord(parts)) {
                    String studentFullName = parts[0].trim();
                    String subject = parts[1].trim();
                    String examType = parts[2].trim();
                    String examName = parts[3].trim();
                    String exerciseName = parts[4].trim();
                    double score = Double.valueOf(parts[5].trim());

                    // Check if the Evaluation already exists in the list
                    Evaluation existingEvaluation = evaluations.stream()
                            .filter(e -> e.getStudentFullName().equals(studentFullName)
                                    && e.getSubject().equals(subject)
                                    && e.getExamName().equals(examName))
                            .findFirst()
                            .orElse(null);

                    if (existingEvaluation != null) {
                        // Add the new exercise to the existing Evaluation's map
                        existingEvaluation.getExercises().put(exerciseName, score);
                    } else {
                        // Prepare exercises map for the new Evaluation
                        Map<String, Double> exercises = new LinkedHashMap<>();
                        exercises.put(exerciseName, score);

                        // Create new Evaluation based on the exam type
                        Evaluation evaluation = createEvaluation(examType, subject, studentFullName, examName, exercises);
                        evaluations.add(evaluation);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Evaluation createEvaluation(String examType, String subject, String studentFullName, String examName, Map<String, Double> exercises) {
        switch (examType.toUpperCase()) {
            case "FINAL_PRACTICAL_WORK":
                return new FinalExam(subject, studentFullName, examName, exercises);
            case "PRACTICAL_WORK":
                return new PracticalExam(subject, studentFullName, examName, exercises);
            case "ORAL_EXAM":
                return new OralExam(subject, studentFullName, examName, exercises);
            case "WRITTEN_EXAM":
                return new WrittenExam(subject, studentFullName, examName, exercises);
            default:
                throw new IllegalArgumentException("Unknown exam type: " + examType);
        }
    }
}
