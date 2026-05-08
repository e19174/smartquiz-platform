package com.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String pin;
    private String status;
    private Integer timeLimitSeconds;
}