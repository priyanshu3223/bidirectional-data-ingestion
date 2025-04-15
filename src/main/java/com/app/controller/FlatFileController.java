package com.app.controller;

import com.app.service.FlatFileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/flatfile")
public class FlatFileController {

    private final FlatFileService flatFileService;

    public FlatFileController(FlatFileService flatFileService) {
        this.flatFileService = flatFileService;
    }

    @PostMapping("/upload")
    public List<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return flatFileService.getFileMetadata(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/preview")
    public List<List<String>> previewFile(@RequestParam("file") MultipartFile file) {
        try {
            return flatFileService.previewFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
