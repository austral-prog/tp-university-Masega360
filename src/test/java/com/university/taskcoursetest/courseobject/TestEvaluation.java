package com.university.taskcoursetest.courseobject;

import java.util.Map;

import com.university.model.evaluation.Evaluation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestEvaluation extends Evaluation {

    public TestEvaluation(String subject, String studentFullName, String examName, Map<String, Double> exercises) {
        super(subject, studentFullName, examName, exercises);
    }

    @Override
    public double calculateGrade() {
        // Just a simple calculation for testing
        return getExercises().values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }
}



class EvaluationTest {

    private TestEvaluation evaluation;

    @BeforeEach
    void setUp() {
        // Create a sample exercises map for testing
        Map<String, Double> exercises = new HashMap<>();
        exercises.put("Exercise 1", 8.0);
        exercises.put("Exercise 2", 9.0);
        exercises.put("Exercise 3", 7.5);

        // Create an instance of TestEvaluation
        evaluation = new TestEvaluation("Math", "John Doe", "Final", exercises);
    }

    @Test
    void testConstructor() {
        assertNotNull(evaluation, "Evaluation instance should not be null");
        assertEquals("Math", evaluation.getSubject(), "Subject should be 'Math'");
        assertEquals("John Doe", evaluation.getStudentFullName(), "Student full name should be 'John Doe'");
        assertEquals("Final", evaluation.getExamName(), "Exam name should be 'Final'");
        assertEquals(3, evaluation.getExercises().size(), "There should be 3 exercises");
    }

    @Test
    void testCalculateGrade() {
        // In this test, we expect an average of the exercise grades.
        double expectedGrade = (8.0 + 9.0 + 7.5) / 3;
        assertEquals(expectedGrade, evaluation.calculateGrade(), "Calculated grade should be the average of exercises");
    }

    @Test
    void testToParsedString() {
        // The parsed string should include the subject, exam name, student full name, and the calculated grade
        String expectedString = "Math,Final,John Doe," + (8.0 + 9.0 + 7.5) / 3;
        assertEquals(expectedString, evaluation.toParsedString(), "toParsedString should return the correct format");
    }

    @Test
    void testSetAndGetId() {
        // Set the ID and check if it's correctly returned
        evaluation.setId(123);
        assertEquals(123, evaluation.getId(), "ID should be set and retrieved correctly");
    }
}
