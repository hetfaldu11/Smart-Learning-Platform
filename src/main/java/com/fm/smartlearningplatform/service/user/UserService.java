package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserVerification;
import com.fm.smartlearningplatform.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // ─── Save ────────────────────────────────────────────────

    @Transactional
    public User save(User user){

        if(userRepository.existsByEmailAndDeletedAtIsNull(user.getEmail())){
            throw new RuntimeException("Email already exists.");
        }

        if(userRepository.existsByPhoneNumberAndDeletedAtIsNull(user.getPhoneNumber())){
            throw new RuntimeException("Phone number already exists.");
        }

        return userRepository.save(user);
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional
    public User findById(Long userId){

        return userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    @Transactional
    public User findByEmail(String email){

        if(email == null || email.isBlank()){
            throw new RuntimeException("Email is null.");
        }

        return userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    @Transactional
    public List<User> findAll(){

        return userRepository.findByDeletedAtIsNull();
    }

    // ─── Update Email ────────────────────────────────────────────────

    @Transactional
    public User updateEmail(Long userId, String newEmail){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if(newEmail == null || newEmail.isBlank()){
            throw new RuntimeException("Email is null.");
        }

        if(userRepository.existsByEmailAndDeletedAtIsNull(newEmail)){
            throw new RuntimeException("Email already exists.");
        }

        UserVerification userVerification = user.getUserVerification();

        if(userVerification == null){
            userVerification = UserVerification.builder()
                    .emailVerified(false)
                    .build();

            user.setUserVerification(userVerification);
        }

        user.setEmail(newEmail);

        return userRepository.save(user);
    }

    // ─── Update Phone number ────────────────────────────────────────────────

    @Transactional
    public User updatePhoneNumber(Long userId, String newPhoneNumber){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if(newPhoneNumber == null || newPhoneNumber.isBlank()){
            throw new RuntimeException("Phone number is null.");
        }

        if(userRepository.existsByPhoneNumberAndDeletedAtIsNull(newPhoneNumber)){
            throw new RuntimeException("phoneNumber already exists.");
        }

        UserVerification userVerification = user.getUserVerification();

        if(userVerification == null){
            userVerification = UserVerification.builder()
                    .phoneVerified(false)
                    .build();

            user.setUserVerification(userVerification);
        }

        user.setPhoneNumber(newPhoneNumber);

        return userRepository.save(user);
    }

    // ─── Update Password Hash ───────────────────────────────────────

    @Transactional
    public User updatePasswordHash(Long userId, String newPasswordHash){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if(newPasswordHash == null || newPasswordHash.isBlank()){

            throw new RuntimeException("Password hash is null.");
        }

        user.setPasswordHash(newPasswordHash);

        user.setPasswordChangedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // ─── Update Enabled ─────────────────────────────────────────────

    @Transactional
    public User updateEnabled(Long userId, int enabled){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        user.setEnabled(enabled);

        return userRepository.save(user);
    }

    // ─── Update Last Login At ───────────────────────────────────────

    @Transactional
    public User updateLastLoginAt(Long userId, LocalDateTime lastLoginAt){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if(lastLoginAt == null){
            throw new RuntimeException("Last login at is null.");
        }

        user.setLastLoginAt(lastLoginAt);

        return userRepository.save(user);
    }

    // ─── Update Failed Login Attempt ────────────────────────────────

    @Transactional
    public User updateFailedLoginAttempt(Long userId, int failedLoginAttempt){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if(failedLoginAttempt < 0){
            throw new RuntimeException("Failed login attempt invalid.");
        }

        user.setFailedLoginAttempt(failedLoginAttempt);

        return userRepository.save(user);
    }

    // ─── Update Last Seen At ────────────────────────────────────────

    @Transactional
    public User updateLastSeenAt(Long userId, LocalDateTime lastSeenAt){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if(lastSeenAt == null){
            throw new RuntimeException("Last seen at is null.");
        }

        user.setLastSeenAt(lastSeenAt);

        return userRepository.save(user);
    }

    // ─── Update Password Changed At ─────────────────────────────────

    @Transactional
    public User updatePasswordChangedAt(Long userId, LocalDateTime passwordChangedAt){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if(passwordChangedAt == null){
            throw new RuntimeException("Password changed at is null.");
        }

        user.setPasswordChangedAt(passwordChangedAt);

        return userRepository.save(user);
    }

    // ─── Update Account Locked Until ────────────────────────────────

    @Transactional
    public User updateAccountLockedUntil(Long userId, LocalDateTime accountLockedUntil){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        user.setAccountLockedUntil(accountLockedUntil);

        return userRepository.save(user);
    }

    // ─── Soft Delete ────────────────────────────────────────────────

    @Transactional
    public User softDeleteUser(Long userId){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if(user.getDeletedAt() != null){
            throw new RuntimeException("User already deleted.");
        }

        user.setDeletedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // ─── Restore User ───────────────────────────────────────────────

    @Transactional
    public User restoreUser(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if(user.getDeletedAt() == null){
            throw new RuntimeException("User already active.");
        }

        user.setDeletedAt(null);

        return userRepository.save(user);
    }

    // ─── Exists ────────────────────────────────────────────────

    @Transactional
    public boolean existsById(Long userId){

        return userRepository.existsByIdAndDeletedAtIsNull(userId);
    }

    @Transactional
    public boolean existsByEmail(String email){

        if(email == null || email.isBlank()){
            throw new RuntimeException("Email is null.");
        }

        return userRepository.existsByEmailAndDeletedAtIsNull(email);
    }
}