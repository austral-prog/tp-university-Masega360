package com.university.model;

import com.university.model.cli.Entity;
import com.university.model.criteria.Criteria;
import com.university.model.evaluation.Evaluation;
import com.university.model.student.Recordable;

import java.util.List;
import java.util.stream.Collectors;

public class Evaluated implements Recordable, Entity {
    private final String studentName;
    private final String subject;
    private final List<Evaluation> filteredEvaluations;
    private final Criteria criteria;
    private final boolean criteriaMet;
    private int id;

    public Evaluated(String studentName, String subject, List<Evaluation> filteredEvaluations, Criteria criteria, boolean criteriaMet) {
        this.studentName = studentName;
        this.subject = subject;
        this.filteredEvaluations = filteredEvaluations;
        this.criteria = criteria;
        this.criteriaMet = criteriaMet;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getSubject() {
        return subject;
    }

    public List<Evaluation> getFilteredEvaluations() {
        return filteredEvaluations;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public boolean isCriteriaMet() {
        return criteriaMet;
    }
    public String getEvaluationNames() {
        return filteredEvaluations.stream()
                .map(Evaluation::getExamName)  // Assuming Evaluation has a getName() method
                .collect(Collectors.joining(", "));
    }

    @Override
    public String toParsedString() {
        return studentName + ',' + subject + "," + getEvaluationNames() + ",Approved:" + criteriaMet;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;

    }
}
