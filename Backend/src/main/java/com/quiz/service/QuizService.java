package com.quiz.service;

import com.quiz.entity.Quiz;
import com.quiz.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz createQuiz(Quiz quiz) {
        quiz.setStatus("CREATED");
        quiz.setPin(String.valueOf(100000 + new Random().nextInt(900000)));
        return quizRepository.save(quiz);
    }

    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    public Quiz getByPin(String pin) {
        return quizRepository.findByPin(pin)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }
}