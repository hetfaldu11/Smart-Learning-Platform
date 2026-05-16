package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.UserPreference;
import com.fm.smartlearningplatform.model.UserVerification;

public interface UserPreferenceService {
    public void save(UserPreference userPreference);

    public UserPreference findById(Long id);

    public void deleteById(Long id);
}
