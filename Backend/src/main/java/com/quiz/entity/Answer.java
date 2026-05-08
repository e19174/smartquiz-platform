package com.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Participant participant;

    @ManyToOne
    private Question question;

    private String selectedAnswer;
    private Boolean correct;
    private Integer marksAwarded;
}