package com.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerStatsDTO {

    private int optionA;
    private int optionB;
    private int optionC;
    private int optionD;
}