package com.fm.smartlearningplatform.verification.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.EmailNotVerifiedException;
import com.fm.smartlearningplatform.exceptionhandler.exception.InvalidPasswordException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import com.fm.smartlearningplatform.verification.dto.request.EnableTwoFactorRequest;
import com.fm.smartlearningplatform.verification.dto.response.UserVerificationResponse;
import com.fm.smartlearningplatform.verification.mapper.UserVerificationMapper;
import com.fm.smartlearningplatform.verification.model.UserVerification;
import com.fm.smartlearningplatform.verification.repository.UserVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TwoFactorVerificationService {

    private final UserRepository userRepository;
    private final UserVerificationRepository userVerificationRepository;
    private final UserVerificationMapper userVerificationMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public void validateTwoFactorNotVerified(Long userId) {
        if (userVerificationRepository.existsByIdAndTwoFactorEnabledIsTrue(userId)) {
            throw new DuplicateResourceException("Two factor is already enabled.");
        }
    }

    @Transactional
    public UserVerificationResponse enableTwoFactor(Long userId, EnableTwoFactorRequest request) {

        validateTwoFactorNotVerified(userId);

        User user = getUser(userId);

        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new InvalidPasswordException("Password is Invalid.");
        }

        UserVerification verification = getVerification(userId);

        if (!verification.isEmailVerified()) {
            throw new EmailNotVerifiedException("Email must be verified before enabling two-factor authentication.");
        }

        verification.setTwoFactorEnabled(true);
        verification.setTwoFactorEnabledAt(LocalDateTime.now());

        return userVerificationMapper.toResponse(verification);
    }

    @Transactional
    public UserVerificationResponse disableTwoFactor(Long userId, EnableTwoFactorRequest request) {
        User user = getUser(userId);

        UserVerification verification = getVerification(userId);

        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new InvalidPasswordException("Password is Invalid.");
        }

        if (!verification.isTwoFactorEnabled()) {
            throw new DuplicateResourceException("Two-factor authentication is already disabled.");
        }

        verification.setTwoFactorEnabled(false);
        verification.setTwoFactorEnabledAt(null);

        return userVerificationMapper.toResponse(verification);
    }


    // Helper

    private UserVerification getVerification(Long id) {
        return userVerificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User verification not found"));
    }

    private User getUser(Long id) {
        return userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }
}
