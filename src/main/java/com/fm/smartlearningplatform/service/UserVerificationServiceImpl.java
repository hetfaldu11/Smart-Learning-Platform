package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.UserProfile;
import com.fm.smartlearningplatform.model.UserVerification;
import com.fm.smartlearningplatform.repository.UserVerificationRepository;
import com.fm.smartlearningplatform.repository.UserVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserVerificationServiceImpl implements UserVerificationService{
    @Autowired
    UserVerificationRepository userVerificationRepository;

    @Override
    public void save(UserVerification userVerification) {
        userVerificationRepository.save(userVerification);
    }

    @Override
    public UserVerification findById(Long id) {
        return userVerificationRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        userVerificationRepository.deleteById(id);
    }
}
