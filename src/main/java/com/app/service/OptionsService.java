package com.app.service;

import com.app.model.Options;
import com.app.model.Question;
import com.app.repository.OptionsRepository;
import com.app.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OptionsService {

    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Transactional
    public Options addOption(Options option, Long questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question != null) {
            // If this new option is marked as correct, unmark any other correct options
            if (option.isCorrect()) {
                question.getOptions().forEach(existingOption -> {
                    if (existingOption.isCorrect()) {
                        existingOption.setCorrect(false);
                        optionsRepository.save(existingOption);
                    }
                });
            }

            option.setQuestion(question);
            return optionsRepository.save(option);
        }
        return null;
    }

    @Transactional
    public Options updateOption(Long id, Options updatedOption) {
        Options existingOption = optionsRepository.findById(id).orElse(null);
        if (existingOption != null) {
            // If this option is being marked as correct, unmark any other correct options
            if (!existingOption.isCorrect() && updatedOption.isCorrect()) {
                Question question = existingOption.getQuestion();
                question.getOptions().forEach(otherOption -> {
                    if (otherOption.getId() != existingOption.getId() && otherOption.isCorrect()) {
                        otherOption.setCorrect(false);
                        optionsRepository.save(otherOption);
                    }
                });
            }

            existingOption.setText(updatedOption.getText());
            existingOption.setCorrect(updatedOption.isCorrect());
            return optionsRepository.save(existingOption);
        }
        return null;
    }

    public List<Options> getAllOptions() {
        return optionsRepository.findAll();
    }

    public Options getOptionById(Long id) {
        return optionsRepository.findById(id).orElse(null);
    }

    public List<Options> getOptionsByQuestionId(Long questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question != null) {
            return question.getOptions();
        }
        return List.of();
    }

    public boolean deleteOption(Long id) {
        if (optionsRepository.existsById(id)) {
            optionsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
