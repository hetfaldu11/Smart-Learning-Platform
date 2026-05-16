package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.User;
import com.fm.smartlearningplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(User user){
        userRepository.save(user);
    }

    @Override
    public User findById(long id){
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUserWithSkill(long id) {
        return userRepository.findUserWithSkills(id).orElse(null);
    }

    @Override
    public User findUserWithInterest(long id) {
        return userRepository.findUserWithInterests(id).orElse(null);
    }

    @Override
    public User findUserWithUserAuthorization(long id) {
        return userRepository.findUserWithUserAuthorizations(id).orElse(null);
    }

    @Override
    public User findUserWithUserSocialLink(long id) {
        return userRepository.findUserWithUserSocialLinks(id).orElse(null);
    }

    @Override
    public User findFullUser(long id) {
        return userRepository.findFullUser(id).orElse(null);
    }


}
