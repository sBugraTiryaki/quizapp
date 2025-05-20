package com.app.service;

import com.app.repository.OptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionsService {

    @Autowired
    private OptionsRepository optionsRepository;

}
