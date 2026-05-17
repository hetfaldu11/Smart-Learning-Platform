package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


public interface UserService {

    void saveUser(User user);

    User findById(long id);

    void deleteById(long id);

    User findUserWithSkill(long id);

    User findUserWithInterest(long id);

    User findUserWithUserAuthorization(long id);

    User findUserWithUserSocialLink(long id);

    User findFullUser(long id);
}