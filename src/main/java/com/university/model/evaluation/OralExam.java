package com.university.model.evaluation;

import java.util.Map;

public class OralExam extends Evaluation {

    public OralExam(String subject, String studentFullName, String examName, Map<String, Double> exercises) {
        super(subject, studentFullName, examName, exercises);
    }

    @Override
    public double calculateGrade() {
        // For Oral Exam, the grade is the only exercise grade (no sum or averaging)
        return getExercises().values().stream().findFirst().orElse(0.0);
    }
}
