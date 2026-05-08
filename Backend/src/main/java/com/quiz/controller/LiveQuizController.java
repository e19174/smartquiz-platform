package com.quiz.controller;

import com.quiz.entity.Question;
import com.quiz.service.LiveQuizService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/live")
@CrossOrigin("*")
public class LiveQuizController {

    private final LiveQuizService liveQuizService;

    public LiveQuizController(LiveQuizService liveQuizService) {
        this.liveQuizService = liveQuizService;
    }

    @GetMapping("/{quizId}")
    public List<Question> getQuestions(@PathVariable Long quizId) {
        return liveQuizService.getQuizQuestions(quizId);
    }
}