package com.app.controller;

import com.app.service.IngestionService;
import com.app.model.IngestionRequest;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/ingestion")
public class IngestionController {

    private final IngestionService ingestionService;

    public IngestionController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    // ClickHouse -> Flat File ingestion
    @PostMapping("/clickhouse-to-flatfile")
    public String clickHouseToFlatFile(@RequestParam("host") String host,
                                       @RequestParam("port") String port,
                                       @RequestParam("database") String database,
                                       @RequestParam("user") String user,
                                       @RequestParam("jwt") String jwt,
                                       @RequestBody List<String> selectedColumns,
                                       @RequestParam("outputFile") String outputFile) {
        try {
            ingestionService.clickHouseToFlatFile(host, port, database, user, jwt, selectedColumns, outputFile);
            return "Ingestion successful!";
        } catch (SQLException | IOException e) {
            return "Ingestion failed: " + e.getMessage();
        }
    }

    // Flat File -> ClickHouse ingestion
    @PostMapping("/flatfile-to-clickhouse")
    public String flatFileToClickHouse(@RequestParam("host") String host,
                                       @RequestParam("port") String port,
                                       @RequestParam("database") String database,
                                       @RequestParam("user") String user,
                                       @RequestParam("jwt") String jwt,
                                       @RequestBody List<String> selectedColumns,
                                       @RequestParam("file") MultipartFile file) {
        try {
            ingestionService.flatFileToClickHouse(host, port, database, user, jwt, selectedColumns, file);
            return "Ingestion successful!";
        } catch (SQLException | IOException e) {
            return "Ingestion failed: " + e.getMessage();
        }
    }
}
