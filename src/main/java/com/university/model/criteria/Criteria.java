package com.university.model.criteria;

import com.university.model.cli.Entity;
import com.university.model.evaluation.Evaluation;
import com.university.model.student.Recordable;

import java.util.ArrayList;
import java.util.List;

public abstract class Criteria implements Recordable, Entity {

    private final String subject;
    private final double threshold;
    private final List<String> evaluationNames;
    private Integer id;


    public Criteria() {
        this.subject = "";
        this.threshold = 0;
        this.evaluationNames = new ArrayList<>();
    }
    public Criteria(String subject, double threshold, String[] evaluationNames) {
        this.subject = subject;
        this.threshold = threshold;
        this.evaluationNames = List.of(evaluationNames);
    }

    public String getSubject() {
        return subject;
    }

    public double getThreshold() {
        return threshold;
    }

    public List<String> getEvaluationNames() {
        return evaluationNames;
    }

    // Abstract method for evaluating whether criteria are met
    public abstract boolean isCriteriaMet(List<Evaluation> evaluations);

    @Override
    public String toParsedString() {
        return subject + "," + threshold  + "," + getEvaluationNames();
    }
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
