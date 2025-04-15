package com.app.controller;

import com.app.model.ClickHouseConnectionRequest;
import com.app.service.ClickHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ClickHouseService clickHouseService;

    // Other existing endpoints

    // Endpoint to test ClickHouse connection
    @PostMapping("/test-clickhouse")
    public ResponseEntity<String> testClickHouseConnection(@RequestBody ClickHouseConnectionRequest request) {
        String result = clickHouseService.testConnection(request);
        return ResponseEntity.ok(result); // Return the result of the connection test
    }
}
