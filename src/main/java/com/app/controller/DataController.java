package com.app.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

@RestController
public class DataController {

    @GetMapping("/fetch-data")
    public ResponseEntity<String> fetchData() {
        // Logic to fetch data from ClickHouse or return appropriate response
        return ResponseEntity.ok("Data fetched successfully");
    }
}

