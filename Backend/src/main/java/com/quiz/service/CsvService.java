package com.quiz.service;

import com.opencsv.CSVReader;
import com.quiz.entity.Question;
import com.quiz.entity.Quiz;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.List;

@Service
public class CsvService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public CsvService(QuizRepository quizRepository,
                      QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    public String upload(Long quizId, MultipartFile file) throws Exception {

        Quiz quiz = quizRepository.findById(quizId).orElseThrow();

        CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
        List<String[]> rows = reader.readAll();

        for (int i = 1; i < rows.size(); i++) {

            String[] r = rows.get(i);

            Question q = new Question();

            q.setQuiz(quiz);
            q.setQuestionText(r[0]);
            q.setOptionA(r[1]);
            q.setOptionB(r[2]);
            q.setOptionC(r[3]);
            q.setOptionD(r[4]);
            q.setCorrectAnswer(r[5]);
            q.setTimeLimit(Integer.parseInt(r[6]));
            q.setMarks(Integer.parseInt(r[7]));
            q.setQuestionOrder(i);

            questionRepository.save(q);
        }

        return "Questions Uploaded Successfully";
    }
}