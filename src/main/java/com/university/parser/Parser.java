package com.university.parser;

import com.university.model.student.Student;

import java.util.List;

public interface Parser<T> {

    default boolean validateRecord(String[] record) {
        return record != null && record.length > 0;
    }
    void parse(String filePath, List<T> parsedRecords);
}
