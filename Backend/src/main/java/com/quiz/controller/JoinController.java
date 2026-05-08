package com.quiz.controller;

import com.quiz.entity.Participant;
import com.quiz.entity.Quiz;
import com.quiz.repository.ParticipantRepository;
import com.quiz.repository.QuizRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/join")
@CrossOrigin("*")
public class JoinController {

    private final QuizRepository quizRepository;
    private final ParticipantRepository participantRepository;

    public JoinController(QuizRepository quizRepository,
                          ParticipantRepository participantRepository) {
        this.quizRepository = quizRepository;
        this.participantRepository = participantRepository;
    }

    @PostMapping
    public Participant join(
            @RequestParam String pin,
            @RequestParam String name
    ) {

        Quiz quiz = quizRepository.findByPin(pin).orElseThrow();

        Participant p = new Participant();
        p.setQuiz(quiz);
        p.setStudentName(name);

        return participantRepository.save(p);
    }
}