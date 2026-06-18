package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.user.request.CreateUserRequest;
import com.fm.smartlearningplatform.user.dto.user.request.PatchUserRequest;
import com.fm.smartlearningplatform.user.dto.user.response.DeleteUserResponse;
import com.fm.smartlearningplatform.user.dto.user.response.UserResponse;
import com.fm.smartlearningplatform.user.mapper.UserMapper;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import com.fm.smartlearningplatform.verification.model.UserVerification;
import com.fm.smartlearningplatform.verification.repository.UserVerificationRepository;
import com.fm.smartlearningplatform.verification.service.EmailVerificationService;
import com.fm.smartlearningplatform.verification.service.PhoneVerificationService;
import com.fm.smartlearningplatform.verification.service.TwoFactorVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;
    private final PhoneVerificationService phoneVerificationService;
    private final UserVerificationRepository userVerificationRepository;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public UserResponse create(CreateUserRequest request) {
        validateEmailNotExist(request.email());
        validatePhoneNumberNotExist(request.phoneNumber());
        User user = userMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.password()));

//        user.setUserVerification(userVerification);

        UserResponse userResponse = userMapper.toResponse(userRepository.save(user));
        UserVerification userVerification = UserVerification.builder()
                .user(user)
                .build();
        userVerificationRepository.save(userVerification);
        return userResponse;
    }


    // ─── Find ────────────────────────────────────────────────

    public UserResponse findById(Long userId) {
        return userMapper.toResponse(getUser(userId));
    }

    public Page<UserResponse> findAll(Pageable pageable) {

        Page<User> users = userRepository
                .findByDeletedAtIsNull(pageable);

        return users.map(userMapper::toResponse);
    }

    // ─── Update ──────────────────────────────────────────────

    @Transactional
    public UserResponse update(Long userId, PatchUserRequest request) {
        User user = getUser(userId);
        if (request.email() != null && !request.email().equals(user.getEmail())) {
            validateEmailNotExist(request.email());
            emailVerificationService.unverifyEmail(userId);
        }
        if (request.phoneNumber() != null && !request.phoneNumber().equals(user.getPhoneNumber())) {
            validatePhoneNumberNotExist(request.phoneNumber());
            phoneVerificationService.unverifyPhone(userId);
        }
        userMapper.update(request, user);
        return userMapper.toResponse(userRepository.save(user));
    }

    // ─── Soft Delete ─────────────────────────────────────────

    @Transactional
    public DeleteUserResponse delete(Long userId) {
        validateUserExist(userId);
        User user = getUser(userId);
        user.setDeletedAt(LocalDateTime.now());
        return new DeleteUserResponse("User is deleted successfully.");
    }
    // ─── Restore ─────────────────────────────────────────────

    @Transactional
    public UserResponse restore(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User not found."));

        user.setDeletedAt(null);
        return userMapper.toResponse(user);
    }
    // ─── Exists ──────────────────────────────────────────────

    public boolean existsById(Long userId) {
        return userRepository.existsByIdAndDeletedAtIsNull(userId);
    }

    // ─── Helpers ─────────────────────────────────────────────

    private User getUser(Long userId) {
        return userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    private void validateUserExist(Long id) {
        if (!userRepository.existsByIdAndDeletedAtIsNull(id)) {
            throw new DuplicateResourceException("User not found.");
        }
    }

    private void validateEmailNotExist(String email) {

        if (userRepository.existsByEmailAndDeletedAtIsNull(email)) {
            throw new DuplicateResourceException("Email already exists.");
        }
    }

    private void validatePhoneNumberNotExist(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return;
        }
        if (userRepository.existsByPhoneNumberAndDeletedAtIsNull(phoneNumber)) {
            throw new DuplicateResourceException("Phone number already exists.");
        }
    }
}