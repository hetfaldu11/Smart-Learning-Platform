package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.Language;
import com.fm.smartlearningplatform.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageServiceImpl implements LanguageService{

    @Autowired
    LanguageRepository languageRepository;

    @Override
    public void save(Language language) {
        languageRepository.save(language);
    }

    @Override
    public Language findById(Long id) {
        return languageRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        languageRepository.deleteById(id);
    }
}
