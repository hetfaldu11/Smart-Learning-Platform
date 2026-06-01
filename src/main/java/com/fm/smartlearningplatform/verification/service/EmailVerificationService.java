package com.fm.smartlearningplatform.verification.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.repository.UserRepository;
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
public class EmailVerificationService {

    private final UserRepository userRepository;
    private final UserVerificationRepository userVerificationRepository;
    private final UserVerificationMapper userVerificationMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public void validateEmailNotVerified(Long userId) {
        if (userVerificationRepository.existsByIdAndEmailVerifiedIsTrue(userId)) {
            throw new DuplicateResourceException("Email is already verified.");
        }
    }

    @Transactional
    public UserVerificationResponse verifyEmail(Long userId) {
        UserVerification verification = getVerification(userId);

        verification.setEmailVerified(true);
        verification.setEmailVerifiedAt(LocalDateTime.now());
        return userVerificationMapper.toResponse(userVerificationRepository.save(verification));
    }

    @Transactional
    public UserVerificationResponse unverifyEmail(Long userId) {
        UserVerification verification = getVerification(userId);
        if (!verification.isEmailVerified()) {
            throw new DuplicateResourceException("Email is already unverified.");
        }
        verification.setEmailVerified(false);
        verification.setEmailVerifiedAt(null);
        return userVerificationMapper.toResponse(verification);
    }


    // Helper

    private UserVerification getVerification(Long id) {
        return userVerificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User verification not found"));
    }
}
