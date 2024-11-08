package com.university.parser;

import com.university.model.criteria.StudentCourse;
import com.university.model.evaluation.Evaluation;

import java.util.List;

public class StudentCourseParser {

    public void parse(List<StudentCourse> studentCourses, List<Evaluation> evaluations) {
        for (Evaluation evaluation : evaluations) {
            String studentFullName = evaluation.getStudentFullName();
            String subject = evaluation.getSubject();

            // Find an existing StudentCourse for the student and subject
            StudentCourse studentCourse = findStudentCourse(studentCourses, studentFullName, subject);

            if (studentCourse != null) {
                // If the StudentCourse exists, add the Evaluation to it
                studentCourse.addEvaluation(evaluation);
            } else {
                // Otherwise, create a new StudentCourse and add it to the list
                StudentCourse newStudentCourse = new StudentCourse(studentFullName, subject);
                newStudentCourse.addEvaluation(evaluation);
                studentCourses.add(newStudentCourse);
            }
        }
    }

    // Helper method to find a StudentCourse by student name and subject
    private StudentCourse findStudentCourse(List<StudentCourse> studentCourses, String studentName, String subject) {
        for (StudentCourse studentCourse : studentCourses) {
            if (studentCourse.getStudentName().equals(studentName) && studentCourse.getSubject().equals(subject)) {
                return studentCourse;
            }
        }
        return null;
    }
}
