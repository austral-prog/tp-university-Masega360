package com.university.service;

import com.university.model.Evaluated;
import com.university.model.criteria.StudentCourse;
import com.university.model.evaluation.Evaluation;
import com.university.model.student.Student;
import com.university.parser.StudentCourseParser;
import com.university.service.sorters.GenericSorter;
import com.university.writer.CSVWriter;
import com.university.parser.StudentParser;

import java.util.List;
import java.util.ArrayList;

public class StudentCourseService {

    private List<StudentCourse> studentcourses;
    private static List<Evaluated> evaluateds;

    public StudentCourseService() {
        this.studentcourses = new ArrayList<>();// Initialize students as an empty list
        this.evaluateds = new ArrayList<>();
    }
    public static List<Evaluation> examsParse() {
        EvaluationService evaluationService = new EvaluationService();
        evaluationService.loadAndParseEvaluations();// This loads and parses the students into the empty list
        evaluationService.sortEvaluations();
        return evaluationService.getEvaluations();
    }
    public void loadAndParseStudents() {
        StudentCourseParser studentCourseParser = new StudentCourseParser();
        studentCourseParser.parse(studentcourses, examsParse());  // Pass the empty list to the parser
    }

    public void sortStudents(){
        GenericSorter.sort(evaluateds, "getSubject","getStudentName");
    }

    public void loadAndParseEvaluated() {
        EvaluationProcessor evaluationProcessor = new EvaluationProcessor();
        CriteriaService criteriaService = new CriteriaService();
        criteriaService.loadAndParseCriterias();
        evaluationProcessor.applyCriteriaToStudentCourses(criteriaService.getCriterias(), studentcourses, evaluateds);  // Pass the empty list to the parser
    }
    public void writeStudentReport(){
        CSVWriter writer = new CSVWriter();
        String courseHeader = "Student_Name,Subject,Evaluations,Approved";
        writer.writeToCSV("src/main/resources/reports/solution_3.csv", evaluateds, courseHeader);  // Write the students to the report
    }
}
