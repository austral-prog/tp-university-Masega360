package com.university.taskcoursetest.courselogic;

import com.university.model.criteria.Criteria;
import com.university.model.criteria.MaxAboveValue;
import com.university.model.criteria.StudentCourse;
import com.university.model.evaluation.Evaluation;
import com.university.model.evaluation.FinalExam;
import com.university.model.evaluation.OralExam;
import com.university.model.Evaluated;
import com.university.service.EvaluationProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EvaluationProcessorTest {

    private EvaluationProcessor evaluationProcessor;
    private List<StudentCourse> studentCourses;
    private List<Criteria> criteriaList;

    @BeforeEach
    void setUp() {
        evaluationProcessor = new EvaluationProcessor();

        // Set up sample StudentCourse objects
        studentCourses = new ArrayList<>();

        // Create evaluations for Student A and Student B
        Map<String, Double> exercisesA = new HashMap<>();
        exercisesA.put("Exercise1", 90.0);
        exercisesA.put("Exercise2", 85.0);
        Evaluation finalExamA = new FinalExam("Math", "Student A", "Final", exercisesA);

        Map<String, Double> exercisesB = new HashMap<>();
        exercisesB.put("Exercise1", 70.0);
        Evaluation finalExamB = new FinalExam("Math", "Student B", "Final", exercisesB);

        List<Evaluation> evaluations1 = new ArrayList<>();
        evaluations1.add(finalExamA);
        evaluations1.add(new OralExam("Math", "Student A", "Oral", exercisesA));
        StudentCourse studentCourse1 = new StudentCourse("Student A", "Math");
        for (Evaluation evaluation : evaluations1) {
            studentCourse1.addEvaluation(evaluation);
        }


        List<Evaluation> evaluations2 = new ArrayList<>();
        evaluations2.add(finalExamB);
        StudentCourse studentCourse2 = new StudentCourse("Student B", "Math");
        for (Evaluation evaluation : evaluations2) {
            studentCourse2.addEvaluation(evaluation);
        }

        studentCourses.add(studentCourse1);
        studentCourses.add(studentCourse2);

        // Set up sample MaxAboveValue Criteria objects
        String[] evaluationNames = {"Final", "Oral"};
        Criteria criteria = new MaxAboveValue("Math", 80.0, evaluationNames);
        // Subject is Math, threshold is 80, evaluations required are "Final" and "Oral"
        criteriaList = new ArrayList<>();
        criteriaList.add(criteria);
    }

    @Test
    void testApplyCriteriaToStudentCourses() {
        // Act: Process the student courses with the criteria
        List<Evaluated> evaluatedResults = evaluationProcessor.applyCriteriaToStudentCourses(criteriaList, studentCourses, new ArrayList<>());

        // Assert: Ensure that Evaluated objects are created and have the correct data
        assertNotNull(evaluatedResults, "Evaluated results should not be null");
        assertEquals(2, evaluatedResults.size(), "There should be 2 evaluated results");

        // Check Evaluated object for Student A
        Evaluated evaluatedA = evaluatedResults.get(0);
        assertEquals("Student A", evaluatedA.getStudentName());
        assertEquals("Math", evaluatedA.getSubject());
        assertTrue(evaluatedA.isCriteriaMet(), "Student A should meet the criteria");

        // Check Evaluated object for Student B
        Evaluated evaluatedB = evaluatedResults.get(1);
        assertEquals("Student B", evaluatedB.getStudentName());
        assertEquals("Math", evaluatedB.getSubject());
        assertFalse(evaluatedB.isCriteriaMet(), "Student B should not meet the criteria");
    }
}
