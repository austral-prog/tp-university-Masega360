package com.university.writer;

import com.university.model.student.Recordable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {

    // Use generics to accept any class that implements Recordable
    public <T extends Recordable> void writeToCSV(String filePath, List<T> data, String header) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write the header to the CSV file
            writer.append(header).append("\n");

            for (T recordable : data) {
                // Call toParsedString() method for each Recordable object
                writer.append(recordable.toParsedString()).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

