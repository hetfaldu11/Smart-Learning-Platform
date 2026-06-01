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
public class PhoneVerificationService {

    private final UserRepository userRepository;
    private final UserVerificationRepository userVerificationRepository;
    private final UserVerificationMapper userVerificationMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public void validatePhoneNumberNotVerified(Long userId) {
        if (userVerificationRepository.existsByIdAndPhoneVerifiedIsTrue(userId)) {
            throw new DuplicateResourceException("Phone is already verified.");
        }
    }

    @Transactional
    public UserVerificationResponse verifyPhone(Long userId) {
        UserVerification verification = getVerification(userId);

        verification.setPhoneVerified(true);
        verification.setPhoneVerifiedAt(LocalDateTime.now());
        return userVerificationMapper.toResponse(verification);
    }


    @Transactional
    public UserVerificationResponse unverifyPhone(Long userId) {
        UserVerification verification = getVerification(userId);
        if (!verification.isPhoneVerified()) {
            throw new DuplicateResourceException("Phone is already unverified.");
        }
        verification.setPhoneVerified(false);
        verification.setPhoneVerifiedAt(null);
        return userVerificationMapper.toResponse(verification);
    }

    // Helper

    private UserVerification getVerification(Long id) {
        return userVerificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User verification not found"));
    }
}
