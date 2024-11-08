package com.university.service;

import com.university.model.criteria.Criteria;
import com.university.service.sorters.GenericSorter;
import com.university.writer.CSVWriter;
import com.university.parser.CriteriaParser;

import java.util.List;
import java.util.ArrayList;

public class CriteriaService {

    private static List<Criteria> criterias;

    public CriteriaService() {
        this.criterias = new ArrayList<>();  // Initialize students as an empty list
    }

    public void loadAndParseCriterias() {
        CriteriaParser criteriaParser = new CriteriaParser();
        criteriaParser.parse("src/main/resources/db/input_3.csv", criterias);  // Pass the list to parse
        if (criterias.isEmpty()) {
            System.out.println("No criteria loaded. Please check the CSV file path and contents.");
        } else {
            System.out.println("Loaded " + criterias.size() + " criteria, example:" + criterias.get(criterias.size()-1));
        }
    }

    public static List<Criteria> getCriterias() {
        return criterias;
    }

}
