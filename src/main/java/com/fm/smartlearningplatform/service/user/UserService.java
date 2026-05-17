package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.User;

import java.util.List;


public interface UserService {

    void save(User user);

    User findById(long id);

    void deleteById(long id);

    User findUserWithSkill(long id);

    User findUserWithInterest(long id);

    User findUserWithUserAuthorization(long id);

    User findUserWithUserSocialLink(long id);

    User findFullUser(long id);

    List<User> findAll();
}