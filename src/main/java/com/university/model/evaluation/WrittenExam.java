package com.university.model.evaluation;

import java.util.Map;

public class WrittenExam extends Evaluation {

    public WrittenExam(String subject, String studentFullName, String examName, Map<String, Double> exercises) {
        super(subject, studentFullName, examName, exercises);
    }

    @Override
    public double calculateGrade() {
        // For Written Exam, the grade is the average of the exercises
        return getExercises().values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }
}
