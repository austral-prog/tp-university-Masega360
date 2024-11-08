package com.university.model.cli.repository;

import com.university.model.cli.CLIImpl;
import com.university.model.criteria.AverageAboveValue;
import com.university.model.criteria.Criteria;
import com.university.model.criteria.MaxAboveValue;
import com.university.model.criteria.MinAboveValue;
import com.university.model.student.Student;
import com.university.service.CourseService;
import com.university.service.CriteriaService;
import com.university.writer.CSVWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CriteriaRepository implements CRUDRepository<Criteria> {

    // Map to store Criteria by their unique identifier (e.g., by subject or name)
    private static final Map<Integer, Criteria> criteriaStorage = new HashMap<>();
    private static int idn = 1;  // ID counter to assign unique IDs to each Criteria

    // Initialize Criteria Repository (could be populated from a service or database)
    public static void CriteriaRepositoryInitializer() {
        CriteriaService criteriaService = new CriteriaService();
        criteriaService.loadAndParseCriterias();
        ;
        // Populate students from the StudentService
        List<Criteria> criterias = CriteriaService.getCriterias();
        for (Criteria criteria : criterias) {
            criteria.setId(idn); // Assign the current id
            criteriaStorage.put(criteria.getId(), criteria); // Store in map
            idn++; // Increment id by 1 for the next iteration
        }
    }
    public static int getId() {
        return idn;

    }
    @Override
    public void create(Criteria criteria) {
        criteriaStorage.put(idn, criteria); // Assign the criteria an ID and store it
        System.out.println("Created: " + criteria.getSubject() + " criteria with ID: " + idn);
        idn++; // Increment ID counter for the next criteria
    }

    @Override
    public Criteria read(int id) {
        Criteria criteria = criteriaStorage.get(id);
        if (criteria != null) {
            System.out.println("Reading Criteria with ID: " + id);
            return criteria;
        } else {
            System.out.println("No criteria found with ID: " + id);
            return null;
        }
    }

    @Override
    public void update(int id, Criteria criteria) {
        if (criteriaStorage.containsKey(id)) {
            criteriaStorage.put(id, criteria);
            System.out.println("Updated Criteria with ID: " + id);
        } else {
            System.out.println("No criteria found with ID: " + id);
        }
    }

    @Override
    public void delete(int id) {
        Criteria criteria = criteriaStorage.remove(id);
        if (criteria != null) {
            System.out.println("Deleted Criteria with ID: " + id);
        } else {
            System.out.println("No criteria found with ID: " + id);
        }
    }

    @Override
    public String getIdentifier() {
        return "Criteria";
    }

    @Override
    public Class<Criteria> getEntityClass() {
        return Criteria.class;
    }

    // Example of a custom method to retrieve Criteria by subject
    public Criteria findBySubject(String subject) {
        return criteriaStorage.values().stream()
                .filter(c -> c.getSubject().equalsIgnoreCase(subject))
                .findFirst()
                .orElse(null);
    }

    // Example of a custom method to retrieve all Criteria matching certain names
    public Map<Integer, Criteria> findByEvaluationNames(String[] evaluationNames) {
        Map<Integer, Criteria> matchingCriteria = new HashMap<>();
        for (Map.Entry<Integer, Criteria> entry : criteriaStorage.entrySet()) {
            Criteria criteria = entry.getValue();
            for (String name : evaluationNames) {
                if (criteria.getEvaluationNames().contains(name)) {
                    matchingCriteria.put(entry.getKey(), criteria);
                }
            }
        }
        return matchingCriteria;
    }
    public List<String> inputs(){
        List<String> result = new ArrayList<>();
        String studCourses = CLIImpl.userInput("Subject:");
        String threshold = CLIImpl.userInput("Grade to pass criteria:");
        String criteria = CLIImpl.userInput("Threshold Above...\n 1. Max\n 2. Min\n 3.Average");
        String evaluations = CLIImpl.userInput("Evaluation Names(evaluation1,evaluation2,...):");
        result.add(studCourses);
        result.add(threshold);
        result.add(criteria);
        result.add(evaluations);
        return result;
    }
    @Override
    public Criteria constructFromList() {
        List<String> inputs = inputs();
        if (inputs.size() < 4) {
            System.out.println("Insufficient data provided to create Criteria.");
            return null;
        }

        // Assuming inputs have the following structure:
        // 1. Subject
        // 2. Threshold (as a string, needs to be converted to double)
        // 3. Evaluation names (comma-separated list)

        // Extract subject
        String subject = inputs.get(0);

        // Extract threshold and convert it to double
        double threshold;
        try {
            threshold = Double.parseDouble(inputs.get(1));
        } catch (NumberFormatException e) {
            System.out.println("Invalid threshold value. It should be a number.");
            return null;
        }


        // Extract evaluation names (comma-separated string and split into array)
        String[] evaluationNames = inputs.get(4).split(",");

        String examType = inputs.get(3);
        switch (examType) {
            case "1":
                Criteria criteriaMax = new MaxAboveValue(subject, threshold, evaluationNames);
                return criteriaMax;
            case "2":
                Criteria criteriaMin = new MinAboveValue(subject, threshold, evaluationNames);
                return criteriaMin;
            case "3":
                Criteria criteriaAve = new AverageAboveValue(subject, threshold, evaluationNames);
                return criteriaAve;
            default:
                throw new IllegalArgumentException("Unknown exam type: " + examType);
        }
    }
    public List<Criteria> mapToList(){
        List<Criteria> result = new ArrayList<>();
        for (Criteria criteria : criteriaStorage.values()) {
            result.add(criteria);
        }
        return result;
    }
    public void writeCSV() {
        String filePath = "src/main/resources/reports/CLIsolution_3.csv";
        CSVWriter writer = new CSVWriter();
        String Header = "Subject, Threshold, Evaluations";
        writer.writeToCSV(filePath, mapToList(), Header);

    }
    public void showByStudentName(String studentName) {
        System.out.println("Not Avalilable for Criteria");
    }
}
