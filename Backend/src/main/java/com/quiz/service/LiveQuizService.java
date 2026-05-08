package com.quiz.service;

import com.quiz.entity.Question;
import com.quiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveQuizService {

    private final QuestionRepository questionRepository;

    public LiveQuizService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getQuizQuestions(Long quizId) {
        return questionRepository.findByQuizIdOrderByQuestionOrderAsc(quizId);
    }
}