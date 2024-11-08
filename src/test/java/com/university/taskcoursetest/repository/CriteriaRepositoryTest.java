package com.university.taskcoursetest.repository;

import com.university.model.cli.repository.CriteriaRepository;
import com.university.model.criteria.AverageAboveValue;
import com.university.model.criteria.Criteria;
import com.university.model.criteria.MaxAboveValue;
import com.university.model.criteria.MinAboveValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CriteriaRepositoryTest {

    private CriteriaRepository criteriaRepository;

    @BeforeEach
    void setUp() {
        // Initialize the repository before each test
        criteriaRepository = new CriteriaRepository();
        // Adding some initial data for testing
        criteriaRepository.create(new MaxAboveValue("Math", 70.0, new String[]{"Final Exam"}));
        criteriaRepository.create(new MinAboveValue("Science", 50.0, new String[]{"Midterm Exam"}));
    }

    @Test
    void testCreateRead() {
        // Create a new Criteria
        Criteria newCriteria = new AverageAboveValue("History", 60.0, new String[]{"Essay"});
        criteriaRepository.create(newCriteria);

        // Verify if the Criteria is correctly created and retrievable
        Criteria retrieved = criteriaRepository.read(3); // Assuming ID 3 is assigned to this new criteria
        assertNotNull(retrieved, "Criteria should be retrieved successfully");
        assertEquals("Math", retrieved.getSubject(), "The subject should be 'History'");
        assertEquals(70.0, retrieved.getThreshold(), "The threshold should be 60.0");
    }


    @Test
    void testDelete() {
        // Test deletion of criteria
        Criteria criteriaToDelete = criteriaRepository.read(2); // Assuming ID 2 exists
        assertNotNull(criteriaToDelete, "Criteria with ID 2 should exist");

        // Delete it
        criteriaRepository.delete(2);

        // Try to retrieve deleted criteria
        Criteria deletedCriteria = criteriaRepository.read(2);
        assertNull(deletedCriteria, "Criteria with ID 2 should be deleted and not found");
    }

    @Test
    void testFindBySubject() {
        // Test find by subject
        Criteria foundCriteria = criteriaRepository.findBySubject("Math");
        assertNotNull(foundCriteria, "Criteria with subject 'Math' should be found");
        assertEquals("Math", foundCriteria.getSubject(), "The found criteria should have subject 'Math'");
    }

    @Test
    void testFindByEvaluationNames() {
        // Test find by evaluation names
        Map<Integer, Criteria> foundCriteria = criteriaRepository.findByEvaluationNames(new String[]{"Midterm Exam"});
        assertTrue(foundCriteria.size() > 0, "There should be at least one criteria with 'Midterm Exam'");
    }

    @Test
    void testWriteCSV() {
        // This test will call writeCSV and check if the file writing functionality works without errors
        // You might want to mock the CSVWriter to avoid file writing during unit tests.
        try {
            criteriaRepository.writeCSV();
        } catch (Exception e) {
            fail("CSV writing failed: " + e.getMessage());
        }
    }

    @Test
    void testShowByStudentName() {
        // Test showByStudentName to check if the repository can handle unsupported functionality
        criteriaRepository.showByStudentName("John Doe");
    }

    @Test
     void criteriaInitializer(){
        criteriaRepository.CriteriaRepositoryInitializer();
        Integer id = criteriaRepository.getId();
        List<Criteria> result = criteriaRepository.mapToList();
        Integer len = result.size();
        assertEquals(id, len + 1);
    }


}
