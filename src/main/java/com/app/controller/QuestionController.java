package com.app.controller;

import com.app.model.Question;
import com.app.model.Quiz;
import com.app.service.QuestionService;
import com.app.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    @GetMapping("/quiz/{quizId}/add-question")
    public String showAddQuestionForm(@PathVariable Long quizId, Model model) {
        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz == null) {
            return "redirect:/";
        }

        model.addAttribute("quiz", quiz);
        model.addAttribute("question", new Question());
        return "add-question";
    }

    @PostMapping("/quiz/{quizId}/add-question")
    public String addQuestion(@PathVariable Long quizId, Question question) {
        questionService.addQuestion(question, quizId);
        return "redirect:/quiz/" + quizId;
    }

    @GetMapping("/question/{id}")
    public String getQuestionById(@PathVariable Long id, Model model) {
        Question question = questionService.getQuestionById(id);
        if (question == null) {
            return "redirect:/";
        }

        model.addAttribute("question", question);
        return "question-details";
    }

    @GetMapping("/question/{id}/edit")
    public String showEditQuestionForm(@PathVariable Long id, Model model) {
        Question question = questionService.getQuestionById(id);
        if (question == null) {
            return "redirect:/";
        }

        model.addAttribute("question", question);
        return "edit-question";
    }

    @PostMapping("/question/{id}/update")
    public String updateQuestion(@PathVariable Long id, Question question) {
        Question updatedQuestion = questionService.updateQuestion(id, question);
        if (updatedQuestion != null) {
            return "redirect:/quiz/" + updatedQuestion.getQuiz().getId();
        }
        return "redirect:/";
    }

    @PostMapping("/question/{id}/delete")
    public String deleteQuestion(@PathVariable Long id) {
        Question question = questionService.getQuestionById(id);
        Long quizId = null;

        if (question != null) {
            quizId = question.getQuiz().getId();
        }

        questionService.deleteQuestion(id);

        if (quizId != null) {
            return "redirect:/quiz/" + quizId;
        }
        return "redirect:/";
    }
}
