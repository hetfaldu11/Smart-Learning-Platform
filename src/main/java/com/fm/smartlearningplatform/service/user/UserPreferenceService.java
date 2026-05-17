package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.UserPreference;

public interface UserPreferenceService {
    public void save(UserPreference userPreference);

    public UserPreference findById(Long id);

    public void deleteById(Long id);
}
