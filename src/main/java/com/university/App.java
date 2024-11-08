package com.university;

import com.university.model.cli.repository.CriteriaRepository;
import com.university.model.cli.repository.StudentRepository;
import com.university.model.cli.repository.EvaluationRepository;
import com.university.model.criteria.Criteria;
import com.university.service.CourseService;
import com.university.service.EvaluationService;
import com.university.service.StudentCourseService;

public class App {
    private static String coursesInputFilePath;
    private static String examsInputFilePath;
    private static String coursesOutputFilePath;
    private static String examsOutputFilePath;

    public App() {
    }

    public static void main(String[] args) {
        coursesApp();
        examsApp();
        criteriaApp();
    }

    public static void coursesApp() {
        System.out.println("Loading and parsing students...");
        CourseService CourseUniversityService = new CourseService();
        CourseUniversityService.loadAndParseStudents();// This loads and parses the students into the empty list
        CourseUniversityService.sortStudents();
        CourseUniversityService.writeStudentReport();// This writes the student report to the output CSV
    }

    public static void examsApp() {
        System.out.println("Loading and parsing exams...");
        EvaluationService evaluationService = new EvaluationService();
        evaluationService.loadAndParseEvaluations();// This loads and parses the students into the empty list
        evaluationService.sortEvaluations();
        evaluationService.writeEvaluationsReport();// This writes the student report to the output CSV
    }
    public static void criteriaApp() {
        System.out.println("Loading and parsing criterias...");
        StudentCourseService StudentCourseUniversityService = new StudentCourseService();
        StudentCourseUniversityService.loadAndParseStudents();// This loads and parses the students into the empty list
        StudentCourseUniversityService.sortStudents();
        StudentCourseUniversityService.loadAndParseEvaluated();
        StudentCourseUniversityService.writeStudentReport();// This writes the student report to the output CS
    }

}

