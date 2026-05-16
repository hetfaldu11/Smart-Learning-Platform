package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.UserProfile;

public interface UserProfileService {
    public void save(UserProfile userProfile);
    public UserProfile findById(Long id);
    public void deleteById(Long id);
}
