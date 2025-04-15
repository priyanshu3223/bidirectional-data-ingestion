package com.app.service;

import com.app.model.ClickHouseConnectionRequest;
import com.app.model.ClickHouseData;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class ClickHouseService {

    public String testConnection(ClickHouseConnectionRequest request) {
        try {
            Properties props = new Properties();
            props.setProperty("user", request.getUser());
            props.setProperty("X-ClickHouse-JWT", request.getJwt());

            String url = String.format("jdbc:clickhouse://%s:%s/%s",
                    request.getHost(), request.getPort(), request.getDatabase());

            Connection conn = DriverManager.getConnection(url, props);
            conn.close();
            return "Connected!";
        } catch (Exception e) {
            return "Connection failed: " + e.getMessage();
        }
    }

    public List<ClickHouseData> fetchData() {
        List<ClickHouseData> dataList = new ArrayList<>();
        try {
            String url = "jdbc:clickhouse://localhost:8123/default"; // Update with actual config
            Properties props = new Properties();
            props.setProperty("user", "default");  // Update as needed

            Connection conn = DriverManager.getConnection(url, props);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM your_table");

            while (rs.next()) {
                ClickHouseData data = new ClickHouseData();
                // Example of populating data (adjust according to your actual columns)
                data.setColumn1(rs.getString("column1"));
                data.setColumn2(rs.getInt("column2"));
                dataList.add(data);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace(); // Log exception
        }
        return dataList;
    }
}
