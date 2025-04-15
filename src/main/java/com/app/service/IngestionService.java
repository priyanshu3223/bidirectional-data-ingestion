package com.app.service;

import com.app.model.IngestionRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Properties;

@Service
public class IngestionService {

    // Method to ingest ClickHouse data into a Flat File
    public void clickHouseToFlatFile(String host, String port, String database, String user, String jwt, 
                                      List<String> selectedColumns, String outputFile) throws SQLException, IOException {
        String url = String.format("jdbc:clickhouse://%s:%s/%s", host, port, database);
        
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("X-ClickHouse-JWT", jwt);

        try (Connection conn = DriverManager.getConnection(url, props)) {
            Statement stmt = conn.createStatement();
            String columnNames = String.join(",", selectedColumns);
            String query = "SELECT " + columnNames + " FROM " + database; // Assuming single table, modify as needed

            ResultSet rs = stmt.executeQuery(query);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            // Writing CSV header (column names)
            writer.write(String.join(",", selectedColumns));
            writer.newLine();

            // Write data to CSV
            while (rs.next()) {
                for (int i = 1; i <= selectedColumns.size(); i++) {
                    writer.write(rs.getString(i));
                    if (i < selectedColumns.size()) writer.write(",");
                }
                writer.newLine();
            }
            writer.close();
        }
    }

    // Method to ingest Flat File data into ClickHouse
    public void flatFileToClickHouse(String host, String port, String database, String user, String jwt, 
                                     List<String> selectedColumns, MultipartFile file) throws SQLException, IOException {
        String url = String.format("jdbc:clickhouse://%s:%s/%s", host, port, database);

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("X-ClickHouse-JWT", jwt);

        try (Connection conn = DriverManager.getConnection(url, props)) {
            // Reading file and inserting data
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));  // This line uses InputStreamReader
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                StringBuilder query = new StringBuilder("INSERT INTO " + database + " (" + String.join(",", selectedColumns) + ") VALUES (");
                for (String value : values) {
                    query.append("'").append(value.trim()).append("',");
                }
                query.deleteCharAt(query.length() - 1); // Remove last comma
                query.append(")");

                Statement stmt = conn.createStatement();
                stmt.execute(query.toString());
            }
        }
    }
}
