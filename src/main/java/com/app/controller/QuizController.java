package com.app.controller;

import com.app.model.Quiz;
import com.app.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("quizzes", quizService.getQuizzes());
        return "quizzes";
    }

    @GetMapping("/add-quiz")
    public String showAddQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "add-quiz";
    }

    @PostMapping("/addQuiz")
    public String addQuiz(Quiz quiz) {
        quizService.addQuiz(quiz);
        return "redirect:/";
    }

    @GetMapping("/quizzes")
    public String getQuizzes(Model model) {
        model.addAttribute("quizzes", quizService.getQuizzes());
        return "quizzes";
    }

    @GetMapping("/quiz/{id}")
    public String getQuizById(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.getQuizById(id);
        model.addAttribute("quiz", quiz);
        return "quiz-details";
    }

    @GetMapping("/quiz/{id}/edit")
    public String showEditQuizForm(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.getQuizById(id);
        model.addAttribute("quiz", quiz);
        return "edit-quiz";
    }

    @PostMapping("/quiz/{id}/update")
    public String updateQuiz(@PathVariable Long id, Quiz quiz) {
        quizService.updateQuiz(id, quiz);
        return "redirect:/quiz/" + id;
    }

    @PostMapping("/quiz/{id}/delete")
    public String deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return "redirect:/";
    }
}
