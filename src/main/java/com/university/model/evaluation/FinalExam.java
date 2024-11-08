package com.university.model.evaluation;

import java.util.Map;
import java.util.Objects;

public class FinalExam extends Evaluation {

    public FinalExam(String subject, String studentFullName, String examName, Map<String, Double> exercises) {
        super(subject, studentFullName, examName, exercises);
    }

    @Override
    public double calculateGrade() {
        // For Final Exam, grade is the sum of the exercises
        if (Objects.equals(getStudentFullName(), "Alice Azure") && Objects.equals(getExamName(), "TP3")){
        System.out.println("Exercises Map: " + getExamName() + getExercises());} // Debugging line
        return getExercises().values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
