package com.quiz.controller;

import com.quiz.entity.Quiz;
import com.quiz.service.CsvService;
import com.quiz.service.QuizService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin("*")
public class QuizUploadController {

    private final QuizService quizService;
    private final CsvService csvService;

    public QuizUploadController(QuizService quizService, CsvService csvService) {
        this.quizService = quizService;
        this.csvService = csvService;
    }

    @PostMapping("/upload")
    public Quiz uploadQuiz(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title
    ) throws Exception {

        //Step 1: Create quiz
        Quiz quiz = new Quiz();
        quiz.setTitle(title);

        quiz = quizService.createQuiz(quiz); // generates PIN

        //Step 2: Upload CSV using quizId
        csvService.upload(quiz.getId(), file);

        //Step 3: Return quiz (id + pin)
        return quiz;
    }
}