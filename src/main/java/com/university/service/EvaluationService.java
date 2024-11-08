package com.university.service;

import com.university.model.evaluation.Evaluation;
import com.university.model.student.Student;
import com.university.parser.EvaluationParser;
import com.university.parser.StudentParser;
import com.university.service.sorters.GenericSorter;
import com.university.writer.CSVWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EvaluationService {
    private static List<Evaluation> evaluations;

    public EvaluationService() {
        this.evaluations = new ArrayList<>();  // Initialize students as an empty list
    }



    public void loadAndParseEvaluations() {
        EvaluationParser evaluationParser = new EvaluationParser();
        evaluationParser.parse("db/input_2.csv", evaluations);  // Pass the empty list to the parser
    }

    public void sortEvaluations(){
        GenericSorter.sort(evaluations, "getSubject","getExamName","getStudentFullName");
    }



    public void writeEvaluationsReport(){
        CSVWriter writer = new CSVWriter();
        String courseHeader = "Subject_Name,Evaluation_Name,Student_Name,Grade";
        writer.writeToCSV("src/main/resources/reports/solution_2.csv", evaluations, courseHeader);  // Write the students to the report
    }

    public static List<Evaluation> getEvaluations() {
        return evaluations;
    }

}


