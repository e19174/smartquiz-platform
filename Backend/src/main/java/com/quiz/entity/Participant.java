package com.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "participant")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentName;
    private Integer score = 0;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}