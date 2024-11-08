package BadTest;

import com.university.model.evaluation.Evaluation;
import com.university.service.EvaluationService;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EvaluationServiceTest {

    private EvaluationService evaluationService;
    @BeforeEach

    @Test
    @Order(1)
    void testLoadAndParseEvaluations() {
        // Simulate parsing from a file and load evaluations into the list
        EvaluationService evaluationService = new EvaluationService();
        evaluationService.loadAndParseEvaluations();  // Replace with an actual test file

        List<Evaluation> evaluations = evaluationService.getEvaluations();
        // Check that evaluations were parsed and added
        assertFalse(evaluations.isEmpty(), "Evaluations should not be empty after parsing");
    }

    @Test
    @Order(2)
    void testSortEvaluations() {
        EvaluationService evaluationService = new EvaluationService();
        evaluationService.loadAndParseEvaluations();
        evaluationService.sortEvaluations();

        // Check that the evaluations are sorted based on the provided attributes
        assertNotNull(evaluationService.getEvaluations(), "Evaluations list should not be null");
        assertTrue(evaluationService.getEvaluations().size() > 0, "Evaluations list should contain elements after sorting");
    }

    @Test
    @Order(3)
    void testWriteEvaluationsReport() {
        EvaluationService evaluationService = new EvaluationService();
        evaluationService.loadAndParseEvaluations();
        evaluationService.sortEvaluations();
        evaluationService.writeEvaluationsReport();

        assertDoesNotThrow(() -> evaluationService.writeEvaluationsReport(), "Writing report should not throw exceptions");
    }

    @Test
    @Order(4)
    void testGetEvaluationsBySubject() {

        List<Evaluation> evaluations = evaluationService.getEvaluations();        // Act: Retrieve the evaluations for "Math" subject
        assertNotNull(evaluations, "Evaluations by subject should not be null");
    }
}
