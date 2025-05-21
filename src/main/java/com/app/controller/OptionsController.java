package com.app.controller;

import com.app.model.Options;
import com.app.model.Question;
import com.app.service.OptionsService;
import com.app.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OptionsController {

    @Autowired
    private OptionsService optionsService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{questionId}/add-option")
    public String showAddOptionForm(@PathVariable Long questionId, Model model) {
        Question question = questionService.getQuestionById(questionId);
        if (question == null) {
            return "redirect:/";
        }

        model.addAttribute("question", question);
        model.addAttribute("option", new Options());
        return "add-option";
    }

    @PostMapping("/question/{questionId}/add-option")
    public String addOption(@PathVariable Long questionId, Options option) {
        optionsService.addOption(option, questionId);
        Question question = questionService.getQuestionById(questionId);
        return "redirect:/quiz/" + question.getQuiz().getId();
    }

    @GetMapping("/option/{id}/edit")
    public String showEditOptionForm(@PathVariable Long id, Model model) {
        Options option = optionsService.getOptionById(id);
        if (option == null) {
            return "redirect:/";
        }

        model.addAttribute("option", option);
        model.addAttribute("question", option.getQuestion());
        return "edit-option";
    }

    @PostMapping("/option/{id}/update")
    public String updateOption(@PathVariable Long id, Options option) {
        Options updatedOption = optionsService.updateOption(id, option);
        if (updatedOption != null) {
            Question question = updatedOption.getQuestion();
            return "redirect:/quiz/" + question.getQuiz().getId();
        }
        return "redirect:/";
    }

    @PostMapping("/option/{id}/delete")
    public String deleteOption(@PathVariable Long id) {
        Options option = optionsService.getOptionById(id);
        Long quizId = null;

        if (option != null) {
            Question question = option.getQuestion();
            quizId = question.getQuiz().getId();
        }

        optionsService.deleteOption(id);

        if (quizId != null) {
            return "redirect:/quiz/" + quizId;
        }
        return "redirect:/";
    }
}
