package com.university.model.criteria;

import com.university.model.evaluation.Evaluation;
import com.university.model.student.Recordable;

import java.util.ArrayList;
import java.util.List;

public class StudentCourse implements Recordable {
    private String studentName;
    private String subject;
    private List<Evaluation> evaluations;
    private boolean approved = false;

    public StudentCourse(String studentName, String subject) {
        this.studentName = studentName;
        this.subject = subject;
        this.evaluations = new ArrayList<>();
    }

    public void addEvaluation(Evaluation evaluation) {
        evaluations.add(evaluation);
    }

    public String getStudentName() {
        return studentName;
    }

    public String getSubject() {
        return subject;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isApproved() {
        return approved;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    @Override
    public String toParsedString() {
        return studentName + ","+ subject + ","+ getEvaluations() + ", Approved:"+ isApproved();
    }
}
