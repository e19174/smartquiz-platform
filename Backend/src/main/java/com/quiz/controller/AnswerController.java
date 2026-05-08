package com.quiz.controller;

import com.quiz.entity.*;
import com.quiz.repository.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
@RestController
@RequestMapping("/api/answer")
@CrossOrigin("*")
public class AnswerController {

    private final ParticipantRepository participantRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public AnswerController(
            ParticipantRepository participantRepository,
            QuestionRepository questionRepository,
            AnswerRepository answerRepository,
            SimpMessagingTemplate messagingTemplate) {

        this.participantRepository = participantRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public String submitAnswer(
            @RequestParam Long participantId,
            @RequestParam Long questionId,
            @RequestParam String selectedAnswer
    ) {

        Participant participant = participantRepository.findById(participantId).orElseThrow();
        Question question = questionRepository.findById(questionId).orElseThrow();

        selectedAnswer = selectedAnswer.trim().toUpperCase();

        Answer answer = new Answer();
        answer.setParticipant(participant);
        answer.setQuestion(question);
        answer.setSelectedAnswer(selectedAnswer);

        if (selectedAnswer.equalsIgnoreCase(question.getCorrectAnswer())) {
            answer.setCorrect(true);
            answer.setMarksAwarded(question.getMarks());

            participant.setScore(participant.getScore() + question.getMarks());
            participantRepository.save(participant);

        } else {
            answer.setCorrect(false);
            answer.setMarksAwarded(0);
        }

        System.out.println("Selected Answer = " + selectedAnswer);
        answerRepository.save(answer);
        System.out.println(answerRepository.findAll());



        return "Answer Submitted";
    }
}