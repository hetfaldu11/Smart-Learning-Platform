package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserAuthorization;
import com.fm.smartlearningplatform.model.user.UserRole;
import com.fm.smartlearningplatform.repository.user.UserAuthorizationRepository;
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
    public UserAuthorization findById(Long id) {
        return userAuthorizationRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        userAuthorizationRepository.deleteById(id);
    }

    @Override
    public UserAuthorization findByUserAndRole(User user, UserRole userRole) {
        return userAuthorizationRepository.findByUserAndUserRole(user,userRole).orElse(null);
    }

    @Override
    public UserAuthorization findByUserIdAndRole(Long userId, UserRole userRole) {
        return userAuthorizationRepository.findByUserIdAndUserRole(userId,userRole).orElse(null);
    }
}