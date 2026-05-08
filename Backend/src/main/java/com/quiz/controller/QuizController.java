package com.quiz.controller;

import com.quiz.entity.Quiz;
import com.quiz.service.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin("*")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public Quiz create(@RequestBody Quiz quiz) {
        return quizService.createQuiz(quiz);
    }

    @GetMapping
    public List<Quiz> all() {
        return quizService.getAll();
    }


    @GetMapping("/pin/{pin}")
    public Quiz getByPin(@PathVariable String pin) {
        return quizService.getByPin(pin);
    }
}