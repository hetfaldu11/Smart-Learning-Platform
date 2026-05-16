package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.UserPreference;
import com.fm.smartlearningplatform.model.UserVerification;
import com.fm.smartlearningplatform.repository.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService{

    @Autowired
    UserPreferenceRepository userPreferenceRepository;

    @Override
    public void save(UserPreference userPreference) {
        userPreferenceRepository.save(userPreference);
    }

    @Override
    public UserPreference findById(Long id) {
        return userPreferenceRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        userPreferenceRepository.deleteById(id);
    }
}
