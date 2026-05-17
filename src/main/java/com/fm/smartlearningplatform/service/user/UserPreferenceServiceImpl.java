package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.UserPreference;
import com.fm.smartlearningplatform.repository.user.UserPreferenceRepository;
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
