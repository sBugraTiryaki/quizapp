package com.app.controller;

import com.app.service.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OptionsController {

    @Autowired
    private OptionsService optionsService;

}
