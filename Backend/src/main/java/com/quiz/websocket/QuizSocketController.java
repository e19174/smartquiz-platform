package com.quiz.websocket;

import com.quiz.service.QuizSessionService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class QuizSocketController {

    private final QuizSessionService quizSessionService;

    public QuizSocketController(QuizSessionService quizSessionService) {
        this.quizSessionService = quizSessionService;
    }

    @MessageMapping("/start")
    public void startQuiz(String quizId) {
        System.out.println("START RECEIVED: " + quizId);

        Long id = Long.parseLong(quizId);
        quizSessionService.startQuiz(id);
    }
}