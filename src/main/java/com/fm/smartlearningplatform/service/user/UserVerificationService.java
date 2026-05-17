package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.UserVerification;

public interface UserVerificationService {
    public void save(UserVerification userVerification);

    public UserVerification findById(Long id);

    public void deleteById(Long id);
}
