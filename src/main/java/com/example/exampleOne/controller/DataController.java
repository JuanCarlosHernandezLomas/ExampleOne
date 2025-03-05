package com.example.exampleOne.controller;

import com.example.exampleOne.bo.DataBo;
import com.example.exampleOne.service.DataService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DataController {

    private static final Logger logger = LoggerFactory.getLogger(DataController.class);
    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }


    @GetMapping("/health")
    public ResponseEntity<String> healthCheck()
    {
        logger.info("Health check requested");
        return ResponseEntity.ok("API working correctly");
    }

    @PostMapping("/data")
    public ResponseEntity<DataBo> saveData(@Valid @RequestBody DataBo dataBO) {
        logger.info("Received request to save data: {}", dataBO);
        DataBo savedData = dataService.saveData(dataBO);
        logger.info("Data saved successfully: {}", savedData);
        return ResponseEntity.ok(savedData);
    }
    @GetMapping("/all")
    public ResponseEntity<List<DataBo>> getAllData() {
        logger.info("Request received to get all data");
        List<DataBo> dataList = dataService.getAllData();
        logger.info("Successfully retrieved all data");
        return ResponseEntity.ok(dataList);
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<DataBo> getData(@PathVariable ("id") Long id) {
        logger.info("Fetching data for ID: {}", id);
        ResponseEntity<DataBo> response = dataService.getData(id);
        logger.info("Data retrieved successfully: {}", response.getBody());
        return response;
    }
}