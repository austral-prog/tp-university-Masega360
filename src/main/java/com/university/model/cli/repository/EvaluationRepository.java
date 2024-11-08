package com.university.model.cli.repository;


import com.university.model.cli.CLIImpl;
import com.university.model.evaluation.*;
import com.university.model.student.Student;
import com.university.service.EvaluationService;
import com.university.writer.CSVWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluationRepository implements CRUDRepository<Evaluation> {
    private final Map<Integer, Evaluation> evaluationStorage = new HashMap<>();
    private int idn = 1; // Initialize id to 1 at the start

    public void EvaluationRepositoryInitializer() {
        EvaluationService evaluationService = new EvaluationService();
        evaluationService.loadAndParseEvaluations();
        evaluationService.sortEvaluations();
        // Populate evaluations from the EvaluationService
        List<Evaluation> evaluations = evaluationService.getEvaluations();
        for (Evaluation evaluation : evaluations) {
            evaluation.setId(idn); // Assign the current id
            evaluationStorage.put(evaluation.getId(), evaluation); // Store in map
            idn++; // Increment id by 1 for the next iteration
        }
    }

    @Override
    public void create(Evaluation evaluation) {
        evaluation.setId(idn);
        idn++;
        evaluationStorage.put(evaluation.getId(), evaluation);
        System.out.println("Created:" + evaluation.toParsedString());
    }

    @Override
    public Evaluation read(int id) {
        try {
            return evaluationStorage.get(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void update(int id, Evaluation evaluation) {
        Evaluation prevEvaluation = evaluationStorage.get(id);
        System.out.println("Updating:" + prevEvaluation.toParsedString());
        if (evaluationStorage.containsKey(id)) {
            evaluationStorage.put(id, evaluation);
            System.out.println("Updated to:" + evaluation.toParsedString());
        }
    }

    @Override
    public void delete(int id) {
        System.out.println("Deleting:" + evaluationStorage.get(id).toParsedString());
        evaluationStorage.remove(id);
    }

    @Override
    public String getIdentifier() {
        return "Evaluation";
    }

    @Override
    public Class<Evaluation> getEntityClass() {
        return Evaluation.class;
    }
    @Override
    public void showByStudentName(String studentName) {
        // Use a Map to store the evaluations for the student by their ID
        Map<Integer, String> evaluationsForStudent = new HashMap<>();

        for (Evaluation evaluation : evaluationStorage.values()) {
            if (evaluation.getStudentFullName().equals(studentName)) {
                // Store the evaluation object in the map with its ID as the key and the parsed string as the value
                evaluationsForStudent.put(evaluation.getId(), evaluation.toParsedString());
            }
        }

        // Print the evaluations for the student
        if (evaluationsForStudent.isEmpty()) {
            System.out.println("No evaluations found for student: " + studentName);
        } else {
            System.out.println("Evaluations for student " + studentName + ":");
            evaluationsForStudent.forEach((id, evaluation) -> {
                System.out.println("ID: " + id + " -> " + evaluation);
            });
        }
    }

    public List<String> inputs(){
        List<String> result = new ArrayList<>();
        String sutdname = CLIImpl.userInput("Student Full Name:");
        String studCourses = CLIImpl.userInput("Subject:");
        String evaluationType = CLIImpl.userInput("Type of evaluation:\n 1. Final\n 2. Oral\n 3.Oral\n 4. Written");
        String evaluationName = CLIImpl.userInput("Evaluation Name:");
        String exercises = CLIImpl.userInput("Exercise list(please use format excersicename1:grade1,excersicename2:grade2,...):");
        result.add(sutdname);
        result.add(studCourses);
        result.add(evaluationType);
        result.add(evaluationName);
        result.add(exercises);
        return result;
    }
    @Override
    public Evaluation constructFromList() {
        List<String> inputs = inputs();
        if (inputs.size() < 4) {
            System.out.println("Insufficient data provided to create Evaluation.");
            return null;
        }

        // Assuming inputs have the following structure:
        // 1. Student Full Name
        // 2. Subject
        // 3. Evaluation Type (e.g., "Exam", "Quiz")
        // 4. Evaluation Name
        // 5. Exercises -> Grade format


        String studentFullName = inputs.get(0);
        String subject = inputs.get(1);
        String examType = inputs.get(2);
        String examName = inputs.get(3);
        String exercises = inputs.get(4);

        Map<String, Double> exerciseGradeMap = new HashMap<>();

        // Split the input string by commas to get individual "Exercise: Grade" pairs
        String[] exerciseGradePairs = exercises.split(",\\s*");

        // Loop through each pair and split by ":" to separate the exercise and grade
        for (String pair : exerciseGradePairs) {
            String[] parts = pair.split(":\\s*");

            // Check if the pair is valid (contains both exercise and grade)
            if (parts.length == 2) {
                String exercise = parts[0].trim(); // Exercise name (e.g., Exercise1)
                String grade = parts[1].trim();    // Grade (e.g., Grade1)
                Double gradeDouble = Double.parseDouble(grade);
                // Add the exercise and grade to the map
                exerciseGradeMap.put(exercise, gradeDouble);
            }
        }


        switch (examType) {
            case "1":
                Evaluation Final = new FinalExam(subject, studentFullName, examName, exerciseGradeMap);
                return Final;
            case "2":
                Evaluation Oral = new OralExam(subject, studentFullName, examName, exerciseGradeMap);
                return Oral;
            case "3":
                Evaluation Practical = new PracticalExam(subject, studentFullName, examName, exerciseGradeMap);
                return Practical;
            case "4":
                Evaluation Written = new WrittenExam(subject, studentFullName, examName, exerciseGradeMap);
                return Written;
            default:
                throw new IllegalArgumentException("Unknown exam type: " + examType);


        }
    }
    public List<Evaluation> mapToList(){
        List<Evaluation> result = new ArrayList<>();
        for (Evaluation evaluation : evaluationStorage.values()) {
            result.add(evaluation);
        }
        return result;
    }
    public void writeCSV() {
        String filePath = "src/main/resources/reports/CLIsolution_2.csv";
        CSVWriter writer = new CSVWriter();
        String Header = "Student_Name,Course_Count";
        writer.writeToCSV(filePath, mapToList(), Header);

    }
}
