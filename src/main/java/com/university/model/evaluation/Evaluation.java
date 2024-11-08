package com.university.model.evaluation;

import com.university.model.cli.Entity;
import com.university.model.student.Recordable;

import java.util.HashMap;
import java.util.Map;

public abstract class Evaluation implements Recordable, Entity {

    private final String subject;
    private final String studentFullName;
    private final String examName;
    private final Map<String, Double> exercises;
    private int id;


    public Evaluation() {
        // Initialize fields with default values if needed
        this.subject = "";
        this.studentFullName = "";
        this.examName = "";
        this.exercises = new HashMap<>();
    }

    public Evaluation(String subject, String studentFullName, String examName, Map<String, Double> exercises) {
        this.subject = subject;
        this.studentFullName = studentFullName;
        this.examName = examName;
        this.exercises = exercises;
    }

    // Getter methods
    public String getSubject() {
        return subject;
    }

        public String getStudentFullName() {
        return studentFullName;
    }

    public String getExamName() {
        return examName;
    }

    public Map<String, Double> getExercises() {
        return exercises;
    }

    // Abstract method for grade calculation (specific to each exam type)
    public abstract double calculateGrade();

    // toParsedString method from Recordable interface
    @Override
    public String toParsedString() {
        return subject + "," + examName + "," + studentFullName + "," + calculateGrade();
    }

    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
