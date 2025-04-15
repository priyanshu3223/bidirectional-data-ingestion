package com.app.controller;

import com.app.model.User;
import com.app.repository.UserRepository;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@MultipartConfig
public class FileUploadController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/uploadCsv")
    public ResponseEntity<String> uploadCsv(@RequestParam("csvFile") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header
                }
                String[] values = line.split(",");
                if (values.length == 4) {
                    User user = new User();
                    user.setId(Long.parseLong(values[0].trim()));
                    user.setName(values[1].trim());
                    user.setEmail(values[2].trim());
                    user.setPhoneNumber(values[3].trim());
                    userRepository.save(user);
                }
            }
            return ResponseEntity.ok("File uploaded and data saved.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
