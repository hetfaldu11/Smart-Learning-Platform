package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.UserVerification;

public interface UserVerificationService {
    public void save(UserVerification userVerification);

    public UserVerification findById(Long id);

    public void deleteById(Long id);
}
