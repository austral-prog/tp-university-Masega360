package com.university.service;

import com.university.model.criteria.Criteria;
import com.university.model.criteria.StudentCourse;
import com.university.model.Evaluated;
import com.university.model.evaluation.Evaluation;

import java.util.ArrayList;
import java.util.List;

public class EvaluationProcessor {

    // Processes each Criteria on each StudentCourse and returns a list of Evaluated objects
    public List<Evaluated> applyCriteriaToStudentCourses(List<Criteria> criteriaList, List<StudentCourse> studentCourses,List<Evaluated> evaluatedResults) {

        for (Criteria criteria : criteriaList) {
            for (StudentCourse studentCourse : studentCourses) {
                // Only consider student courses with the same subject as the criteria
                if (studentCourse.getSubject().equals(criteria.getSubject())) {
                    // Filter evaluations in the StudentCourse based on the criteria's required evaluation names
                    List<Evaluation> filteredEvaluations = studentCourse.getEvaluations().stream()
                            .filter(evaluation -> criteria.getEvaluationNames().contains(evaluation.getExamName()))
                            .toList();

                    // Apply the criteria to the filtered evaluations
                    boolean criteriaMet = criteria.isCriteriaMet(filteredEvaluations);

                    // Create an Evaluated instance with the relevant parameters
                    Evaluated evaluated = new Evaluated(
                            studentCourse.getStudentName(),
                            studentCourse.getSubject(),
                            filteredEvaluations,
                            criteria,
                            criteriaMet
                    );

                    // Add the evaluated result to the list
                    evaluatedResults.add(evaluated);
                }
            }
        }

        return evaluatedResults;
    }
}
