package com.university.model.criteria;

import com.university.model.evaluation.Evaluation;
import java.util.List;

public class AverageAboveValue extends Criteria {

    public AverageAboveValue(String subject, double threshold, String[] evaluationNames) {
        super(subject, threshold, evaluationNames);
    }

    @Override
    public boolean isCriteriaMet(List<Evaluation> evaluations) {
        return evaluations.stream()
                .filter(e -> getEvaluationNames().contains(e.getExamName()))
                .mapToDouble(Evaluation::calculateGrade)
                .average()
                .orElse(Double.NaN) >= getThreshold();
    }
}
