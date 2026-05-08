package com.quiz.controller;

import com.quiz.service.CsvService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin("*")
public class UploadController {

    private final CsvService csvService;

    public UploadController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/{quizId}")
    public String upload(
            @PathVariable Long quizId,
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        return csvService.upload(quizId, file);
    }
}
