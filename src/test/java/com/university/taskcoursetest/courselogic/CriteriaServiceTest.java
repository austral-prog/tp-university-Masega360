package com.university.taskcoursetest.courselogic;

import com.university.model.criteria.Criteria;
import com.university.parser.CriteriaParser;
import com.university.service.CriteriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class CriteriaServiceTest {

    private CriteriaService criteriaService;

    @BeforeEach
    void setUp() {
        // Initialize CriteriaService before each test
        criteriaService = new CriteriaService();
    }

    @Test
    void testLoadAndParseCriterias() {
        // Given: A predefined CSV file or mocked data
        criteriaService.loadAndParseCriterias(); // Parsing the file "src/main/resources/db/input_3.csv"

        // When: Criteria is loaded
        List<Criteria> criterias = CriteriaService.getCriterias();

        // Then: Verify that the criteria list is not empty and loaded correctly
        assertFalse(criterias.isEmpty(), "Criteria list should not be empty after parsing");
        // Optionally, you can check for specific values in the loaded list if needed.
    }

    @Test
    void testGetCriterias() {
        // Given: Ensure the list is populated
        criteriaService.loadAndParseCriterias(); // Loading criteria from the CSV

        // When: Calling getCriterias
        List<Criteria> criterias = CriteriaService.getCriterias();

        // Then: Validate that the list of criteria is returned correctly
        assertNotNull(criterias, "Criterias should not be null");
        assertTrue(criterias.size() > 0, "Criterias list should contain at least one element");
    }
}
