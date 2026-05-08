package com.quiz.repository;

import com.quiz.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByParticipantId(Long participantId);

    @Query("SELECT COUNT(a) FROM Answer a WHERE a.question.id = :questionId AND a.selectedAnswer = :option")
    int countByQuestionAndOption(
            @Param("questionId") Long questionId,
            @Param("option") String option
    );
}