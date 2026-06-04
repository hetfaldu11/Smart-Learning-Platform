package com.fm.smartlearningplatform.verification.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.InvalidOtpException;
import com.fm.smartlearningplatform.exceptionhandler.exception.InvalidPasswordException;
import com.fm.smartlearningplatform.exceptionhandler.exception.OtpExpiryException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.otp.model.OtpType;
import com.fm.smartlearningplatform.otp.model.UserOtp;
import com.fm.smartlearningplatform.otp.repository.UserOtpRepository;
import com.fm.smartlearningplatform.otp.service.EmailService;
import com.fm.smartlearningplatform.security.jwt.JWTService;
import com.fm.smartlearningplatform.security.ratelimit.RateLimitService;
import com.fm.smartlearningplatform.security.ratelimit.RateLimitType;
import com.fm.smartlearningplatform.security.usersession.UserSessionService;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import com.fm.smartlearningplatform.user.service.UserService;
import com.fm.smartlearningplatform.verification.dto.request.ChangePasswordRequest;
import com.fm.smartlearningplatform.verification.dto.request.ResetPasswordRequest;
import com.fm.smartlearningplatform.verification.dto.request.VerifyResetOtpRequest;
import com.fm.smartlearningplatform.verification.dto.response.ResetTokenResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class ChangePasswordService {

    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTService jWTService;
    private  final UserOtpRepository userOtpRepository;
    private  final UserSessionService userSessionService;
    private final RateLimitService rateLimitService;


    // ────────────────────── change password ────────────────────────────────────────────────


    @Transactional
    public void changePassword(Long id, ChangePasswordRequest request)
    {
        rateLimitService.consume(RateLimitType.CHANGE_PASSWORD, id.toString());
        User user = getUser(id);

        if (!passwordEncoder.matches(request.oldPassword(), user.getPasswordHash())) {
            throw new InvalidPasswordException("Old password is incorrect.");
        }

        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);

        userSessionService.revokeAllSessions(user.getId());
    }

    // ────────────────────── verify otp ────────────────────────────────────────────────


    @Transactional
    public ResetTokenResponse verifyPasswordResetOtp(VerifyResetOtpRequest request)
    {

        UserOtp userOtp = userOtpRepository
                        .findTopByUserIdAndTypeAndUsedFalseOrderByIdDesc(request.userId(), OtpType.PASSWORD_RESET)
                        .orElseThrow(() -> new ResourceNotFoundException("Otp not found."));

        validateOtp(request.otp(), userOtp);

        userOtp.setUsed(true);

        userOtp.setVerifiedAt(LocalDateTime.now());

        String resetToken = jWTService.generatePasswordResetToken(request.userId());

        return new ResetTokenResponse(resetToken);
    }

    // ────────────────────── reset password ────────────────────────────────────────────────


    @Transactional
    public void resetPassword(ResetPasswordRequest request)
    {

        rateLimitService.consume(RateLimitType.RESET_PASSWORD, request.resetToken());

        Claims claims = jWTService.extractClaims(request.resetToken());

        String purpose = claims.get("purpose", String.class);

        if (!"PASSWORD_RESET".equals(purpose)) {

            throw new InvalidPasswordException("Invalid reset token.");
        }

        Long userId = Long.valueOf(claims.getSubject());

        User user = getUser(userId);

        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);

        userSessionService.revokeAllSessions(user.getId());// logged out from all device
    }

    // ────────────────────── Helper ────────────────────────────────────────────────


    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private void validateOtp(String otp, UserOtp userOtp) {
        if (userOtp.isUsed() || userOtp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new OtpExpiryException("Otp is expired.");
        }
        if (!passwordEncoder.matches(otp, userOtp.getOtpHash())) {
            throw new InvalidOtpException("Otp is invalid.");
        }
    }

}
