package com.app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlatFileService {

    // Method to read the first few rows of a CSV file and return column names
    public List<String> getFileMetadata(MultipartFile file) throws IOException {
        List<String> columns = new ArrayList<>();
        
        // Read the first line to get column names (assuming CSV headers)
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getOriginalFilename()))) {
            String line = reader.readLine();
            if (line != null) {
                String[] header = line.split(",");
                for (String column : header) {
                    columns.add(column.trim());  // Trim to remove any extra spaces
                }
            }
        }
        
        return columns;
    }

    // Optional: Preview first 5 records (rows) of the CSV
    public List<List<String>> previewFile(MultipartFile file) throws IOException {
        List<List<String>> preview = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getOriginalFilename()))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null && count < 5) {
                String[] values = line.split(",");
                List<String> row = new ArrayList<>();
                for (String value : values) {
                    row.add(value.trim());
                }
                preview.add(row);
                count++;
            }
        }
        return preview;
    }
}
