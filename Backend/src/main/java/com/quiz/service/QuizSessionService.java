package com.quiz.service;

import com.quiz.dto.AnswerStatsDTO;
import com.quiz.dto.LeaderboardDTO;
import com.quiz.dto.QuestionDTO;
import com.quiz.entity.*;
import com.quiz.repository.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizSessionService {

    private final QuestionRepository questionRepository;
    private final ParticipantRepository participantRepository;
    private final AnswerRepository answerRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public QuizSessionService(
            QuestionRepository questionRepository,
            ParticipantRepository participantRepository,
            AnswerRepository answerRepository,
            SimpMessagingTemplate messagingTemplate) {

        this.questionRepository = questionRepository;
        this.participantRepository = participantRepository;
        this.answerRepository = answerRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void startQuiz(Long quizId) {

        List<Question> questions =
                questionRepository.findByQuizIdOrderByQuestionOrderAsc(quizId);

        new Thread(() -> {

            for (Question q : questions) {

                // Send Question
                QuestionDTO dto = new QuestionDTO(
                        q.getId(),
                        q.getQuestionText(),
                        q.getOptionA(),
                        q.getOptionB(),
                        q.getOptionC(),
                        q.getOptionD()
                );

                messagingTemplate.convertAndSend(
                        "/topic/quiz/" + quizId + "/question",
                        dto
                );

                // Timer loop
                for (int i = q.getTimeLimit(); i >= 0; i--) {

                    messagingTemplate.convertAndSend(
                            "/topic/quiz/" + quizId + "/timer",
                            i
                    );


                    int count = participantRepository
                            .findByQuizIdOrderByScoreDesc(quizId)
                            .size();

                    messagingTemplate.convertAndSend(
                            "/topic/quiz/" + quizId + "/participants",
                            count
                    );

                    // Send LIVE answer stats to admin
                    AnswerStatsDTO stats = new AnswerStatsDTO(
                            answerRepository.countByQuestionAndOption(q.getId(), "A"),
                            answerRepository.countByQuestionAndOption(q.getId(), "B"),
                            answerRepository.countByQuestionAndOption(q.getId(), "C"),
                            answerRepository.countByQuestionAndOption(q.getId(), "D")
                    );

                    messagingTemplate.convertAndSend(
                            "/topic/quiz/" + quizId + "/stats",
                            stats
                    );

                    sleep(1000);
                }

                // Leaderboard after each question
                List<LeaderboardDTO> leaderboard =
                        participantRepository.findByQuizIdOrderByScoreDesc(quizId)
                                .stream()
                                .map(p -> new LeaderboardDTO(
                                        p.getStudentName(),
                                        p.getScore()
                                ))
                                .toList();

                messagingTemplate.convertAndSend(
                        "/topic/quiz/" + quizId + "/leaderboard",
                        leaderboard
                );
            }

            messagingTemplate.convertAndSend(
                    "/topic/quiz/" + quizId + "/end",
                    "Quiz Finished"
            );

        }).start();
    }

    private void sleep(int ms) {
        try { Thread.sleep(ms); } catch (Exception ignored) {}
    }
}