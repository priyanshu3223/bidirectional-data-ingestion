package com.app.controller;

import com.app.service.ClickHouseService;
import com.app.model.ClickHouseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClickHouseController {

    @Autowired
    private ClickHouseService clickHouseService;

    @GetMapping("/api/data")
    public List<ClickHouseData> fetchClickHouseData() {
        // Fetch the data from ClickHouse via the service layer
        return clickHouseService.fetchData();
    }
}
