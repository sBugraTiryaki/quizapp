package com.app.service;

import com.app.model.Question;
import com.app.model.Quiz;
import com.app.repository.QuestionRepository;
import com.app.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    public Question addQuestion(Question question, Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz != null) {
            question.setQuiz(quiz);
            return questionRepository.save(question);
        }
        return null;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public List<Question> getQuestionsByQuizId(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz != null) {
            return quiz.getQuestions();
        }
        return List.of();
    }

    public Question updateQuestion(Long id, Question updatedQuestion) {
        Question existingQuestion = questionRepository.findById(id).orElse(null);
        if (existingQuestion != null) {
            existingQuestion.setText(updatedQuestion.getText());
            existingQuestion.setCategory(updatedQuestion.getCategory());
            existingQuestion.setDifficulty(updatedQuestion.getDifficulty());
            return questionRepository.save(existingQuestion);
        }
        return null;
    }

    public boolean deleteQuestion(Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
