package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.UserAuthorization;
import com.fm.smartlearningplatform.model.UserAuthorizationId;
import com.fm.smartlearningplatform.repository.UserAuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthorizationServiceImpl implements UserAuthorizationService{

    @Autowired
    UserAuthorizationRepository userAuthorizationRepository;

    @Override
    public void save(UserAuthorization userAuthorization) {
        userAuthorizationRepository.save(userAuthorization);
    }

    @Override
    public UserAuthorization findById(UserAuthorizationId id) {
        return userAuthorizationRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(UserAuthorizationId id) {
        userAuthorizationRepository.deleteById(id);
    }
}