package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.UserProfile;

public interface UserProfileService {
    public void save(UserProfile userProfile);
    public UserProfile findById(Long id);
    public void deleteById(Long id);
}
