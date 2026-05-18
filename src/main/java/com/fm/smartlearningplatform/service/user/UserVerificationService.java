package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserVerification;
import com.fm.smartlearningplatform.repository.user.UserRepository;
import com.fm.smartlearningplatform.repository.user.UserVerificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserVerificationService {

    UserRepository userRepository;

    UserVerificationRepository userVerificationRepository;

    @Autowired
    public UserVerificationService(UserRepository userRepository,
                                   UserVerificationRepository userVerificationRepository) {

        this.userRepository = userRepository;
        this.userVerificationRepository = userVerificationRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public User addUserVerification(User user, UserVerification userVerification){

        if(userRepository.existsByIdAndDeletedAtIsNull(user.getId()) == false){
            throw new RuntimeException("User not found.");
        }

        if(userVerificationRepository.existsById(user.getId())){
            throw new RuntimeException("User verification already exists.");
        }

        user.setUserVerification(userVerification);

        return userRepository.save(user);
    }

    @Transactional
    public User addUserVerification(Long userId, UserVerification userVerification){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if(userVerificationRepository.existsById(userId)){
            throw new RuntimeException("User verification already exists.");
        }

        user.setUserVerification(userVerification);

        return userRepository.save(user);
    }

    // ─── Update  ────────────────────────────────

    @Transactional
    public User updateEmailVerified(Long userId, boolean emailVerified){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        UserVerification userVerification = user.getUserVerification();

        if(userVerification == null){
            throw new RuntimeException("User verification not found.");
        }

        userVerification.setEmailVerified(emailVerified);

        if(emailVerified){
            userVerification.setEmailVerifiedAt(LocalDateTime.now());
        }
        else{
            userVerification.setEmailVerifiedAt(null);
        }

        return userRepository.save(user);
    }


    @Transactional
    public User updatePhoneVerified(Long userId, boolean phoneVerified){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        UserVerification userVerification = user.getUserVerification();

        if(userVerification == null){
            throw new RuntimeException(" verification not found.");
        }

        userVerification.setPhoneVerified(phoneVerified);

        if(phoneVerified){userVerification.setPhoneVerifiedAt(LocalDateTime.now());
        }
        else{
            userVerification.setPhoneVerifiedAt(null);
        }

        return userRepository.save(user);
    }


    @Transactional
    public User updateTwoFactorEnabled(Long userId, boolean twoFactorEnabled){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        UserVerification userVerification = user.getUserVerification();

        if(userVerification == null){
            throw new RuntimeException("User verification not found.");
        }

        userVerification.setTwoFactorEnabled(twoFactorEnabled);

        return userRepository.save(user);
    }
}