package com.university.model.evaluation;

import java.util.Map;

public class PracticalExam extends Evaluation {

    public PracticalExam(String subject, String studentFullName, String examName, Map<String, Double> exercises) {
        super(subject, studentFullName, examName, exercises);
    }

    @Override
    public double calculateGrade() {
        return getExercises().values().stream().reduce((first, second) -> second).orElse(0.0);
    }
}


