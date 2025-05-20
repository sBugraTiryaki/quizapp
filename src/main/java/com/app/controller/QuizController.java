package com.app.controller;

import com.app.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;

}
