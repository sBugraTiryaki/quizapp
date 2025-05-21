package com.app.controller;

import com.app.model.Options;
import com.app.model.Question;
import com.app.model.Quiz;
import com.app.service.OptionsService;
import com.app.service.QuestionService;
import com.app.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/take-quiz")
public class QuizTakingController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private OptionsService optionsService;

    @GetMapping("")
    public String showQuizSelectionPage(Model model) {
        model.addAttribute("quizzes", quizService.getQuizzes());
        return "select-quiz";
    }

    @GetMapping("/{quizId}")
    public String startQuiz(@PathVariable Long quizId, Model model) {
        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz == null || quiz.getQuestions().isEmpty()) {
            return "redirect:/take-quiz";
        }

        model.addAttribute("quiz", quiz);
        model.addAttribute("selectedOptions", new HashMap<Long, Long>());
        return "take-quiz";
    }

    @PostMapping("/{quizId}/submit")
    public String submitQuiz(@PathVariable Long quizId,
                             @RequestParam Map<String, String> answers,
                             Model model) {
        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz == null) {
            return "redirect:/";
        }

        List<Question> questions = quiz.getQuestions();
        int totalQuestions = questions.size();
        int correctAnswers = 0;

        Map<Long, Boolean> resultsMap = new HashMap<>();

        // Process each answer
        for (Question question : questions) {
            Long questionId = question.getId();
            String selectedOptionId = answers.get("question_" + questionId);

            if (selectedOptionId != null && !selectedOptionId.isEmpty()) {
                Long optionId = Long.parseLong(selectedOptionId);
                Options selectedOption = optionsService.getOptionById(optionId);

                // Check if the selected option is correct
                boolean isCorrect = selectedOption != null && selectedOption.isCorrect();
                resultsMap.put(questionId, isCorrect);

                if (isCorrect) {
                    correctAnswers++;
                }
            } else {
                // No answer selected for this question
                resultsMap.put(questionId, false);
            }
        }

        // Calculate score as percentage
        double score = (double) correctAnswers / totalQuestions * 100;

        model.addAttribute("quiz", quiz);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("score", score);
        model.addAttribute("resultsMap", resultsMap);
        model.addAttribute("answers", answers);

        return "quiz-results";
    }
}
