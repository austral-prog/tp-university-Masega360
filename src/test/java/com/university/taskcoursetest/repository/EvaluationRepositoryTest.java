package com.university.taskcoursetest.repository;

import com.university.model.evaluation.*;
import com.university.model.cli.repository.EvaluationRepository;
import com.university.service.EvaluationService;
import com.university.writer.CSVWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class EvaluationRepositoryTest {

    private EvaluationRepository evaluationRepository;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        evaluationRepository = new EvaluationRepository();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testCreate() {
        Evaluation exam = new FinalExam("Math", "John Doe", "Final Math Exam", new HashMap<>());
        evaluationRepository.create(exam);

        String output = outContent.toString();
        assertTrue(output.contains("Created:"));
        assertNotNull(evaluationRepository.read(exam.getId()));
    }

    @Test
    void testRead() {
        Evaluation exam = new FinalExam("Science", "Jane Doe", "Science Exam", new HashMap<>());
        evaluationRepository.create(exam);

        Evaluation retrieved = evaluationRepository.read(exam.getId());
        assertNotNull(retrieved);
        assertEquals("Jane Doe", retrieved.getStudentFullName());
    }

    @Test
    void testUpdate() {
        Evaluation exam = new FinalExam("History", "John Smith", "History Final", new HashMap<>());
        evaluationRepository.create(exam);

        Evaluation updatedExam = new FinalExam("History", "John Smith", "Updated History Final", new HashMap<>());
        evaluationRepository.update(exam.getId(), updatedExam);

        String output = outContent.toString();
        assertTrue(output.contains("Updated to:"));
        assertEquals("Updated History Final", evaluationRepository.read(exam.getId()).getExamName());
    }

    @Test
    void testDelete() {
        Evaluation exam = new FinalExam("Physics", "Alice Doe", "Physics Final", new HashMap<>());
        evaluationRepository.create(exam);

        evaluationRepository.delete(exam.getId());

        String output = outContent.toString();
        assertTrue(output.contains("Deleting:"));
        assertNull(evaluationRepository.read(exam.getId()));
    }

    @Test
    void testShowByStudentName() {
        Evaluation exam1 = new FinalExam("Math", "Alice", "Math Exam", new HashMap<>());
        Evaluation exam2 = new FinalExam("Science", "Alice", "Science Exam", new HashMap<>());
        evaluationRepository.create(exam1);
        evaluationRepository.create(exam2);

        evaluationRepository.showByStudentName("Alice");
        String output = outContent.toString();

        assertTrue(output.contains("Evaluations for student Alice:"));
        assertTrue(output.contains("Math Exam"));
        assertTrue(output.contains("Science Exam"));
    }
}
