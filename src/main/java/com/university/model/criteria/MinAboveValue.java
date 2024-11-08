package com.university.model.criteria;

import com.university.model.evaluation.Evaluation;
import java.util.List;

public class MinAboveValue extends Criteria {

    public MinAboveValue(String subject, double threshold, String[] evaluationNames) {
        super(subject, threshold, evaluationNames);
    }

    @Override
    public boolean isCriteriaMet(List<Evaluation> evaluations) {
        return evaluations.stream()
                .filter(e -> getEvaluationNames().contains(e.getExamName()))
                .mapToDouble(Evaluation::calculateGrade)
                .min()
                .orElse(Double.NaN) >= getThreshold();
    }
}
