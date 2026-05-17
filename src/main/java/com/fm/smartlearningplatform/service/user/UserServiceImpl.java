package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.repository.user.UserRepository;
import com.fm.smartlearningplatform.repository.user.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserSkillRepository userSkillRepository;

    //  ─────Save─────────────────────────────────────────────

    @Override
    public void save(User user){
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }
        userRepository.save(user);
    }

    //  ─────Find─────────────────────────────────────────────

    @Override
    public User findById(long id){
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
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

    //  ─────Delete───────────────────────────────────────────

    @Override
    public void deleteById(long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        if (user.getDeletedAt() != null) {
            throw new RuntimeException("User already deleted");
        }

        userSkillRepository.deleteByUserId(id);

        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
