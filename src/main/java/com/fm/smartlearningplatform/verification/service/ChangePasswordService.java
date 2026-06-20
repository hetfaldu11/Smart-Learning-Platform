package com.fm.smartlearningplatform.verification.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.*;
import com.fm.smartlearningplatform.otp.service.OtpSendService;
import com.fm.smartlearningplatform.security.jwt.JWTService;
import com.fm.smartlearningplatform.security.ratelimit.RateLimitService;
import com.fm.smartlearningplatform.security.usersession.UserSessionService;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import com.fm.smartlearningplatform.util.OtpGenerator;
import com.fm.smartlearningplatform.util.RedisKey;
import com.fm.smartlearningplatform.verification.dto.request.ChangePasswordRequest;
import com.fm.smartlearningplatform.verification.dto.request.PasswordResetRequest;
import com.fm.smartlearningplatform.verification.dto.request.ResetPasswordRequest;
import com.fm.smartlearningplatform.verification.dto.request.VerifyResetOtpRequest;
import com.fm.smartlearningplatform.verification.dto.response.ResetTokenResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor

public class ChangePasswordService {

    private final OtpSendService otpSendService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTService jWTService;
    private final UserSessionService userSessionService;
    private final RateLimitService rateLimitService;
    private final StringRedisTemplate redisTemplate;

    @Value("${otp.change.password.expiry.seconds}")
    private int OTP_EXPIRY_SECONDS;

    @Value("${otp.change.password.resend.seconds}")
    private int OTP_RESEND_SECONDS;


    // ────────────────────── change password ────────────────────────────────────────────────


    @Transactional
    public void changePassword(Long id, ChangePasswordRequest request) {
        User user = getUser(id);

        if (!passwordEncoder.matches(request.oldPassword(), user.getPasswordHash())) {
            throw new InvalidPasswordException("Old password is incorrect.");
        }

        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);

        userSessionService.revokeAllSessions(user.getId());
    }

    // ────────────────────── send password OTP  ────────────────────────────────────────────────

    @Transactional
    public void sendPasswordResetOtp(PasswordResetRequest request) {

        Long userId = userRepository.findByEmailAndDeletedAtIsNull(request.email()).orElseThrow(() -> new ResourceNotFoundException("user Not Found")).getId();
        log.info("Password reset OTP requested for userId: {}", userId);

        String coolDownKey = RedisKey.changePasswordOtpCooldownKey(userId);

        if (Boolean.TRUE.equals(redisTemplate.hasKey(coolDownKey))) {
            throw new OtpWaitException("wait for send another OTP:");
        }

        String otp = OtpGenerator.generate();

        String key = RedisKey.changePasswordOtpKey(userId);

        redisTemplate.opsForValue().set(
                key, passwordEncoder.encode(otp),
                OTP_EXPIRY_SECONDS,
                TimeUnit.SECONDS
        );

        redisTemplate.opsForValue().set(
                coolDownKey, "blocked",
                OTP_RESEND_SECONDS,
                TimeUnit.SECONDS
        );


        otpSendService.sendChangePasswordOtp(request.email(), otp);
    }

    // ────────────────────── verify otp ────────────────────────────────────────────────


    @Transactional
    public ResetTokenResponse verifyPasswordResetOtp(VerifyResetOtpRequest request) {
        Long userId = userRepository.findByEmailAndDeletedAtIsNull(request.email()).orElseThrow(() -> new ResourceNotFoundException("user Not Found")).getId();

        String attemptKey = RedisKey.changePasswordOtpAttemptKey(userId);

        Long attempts = redisTemplate.opsForValue().increment(attemptKey);

        redisTemplate.expire(attemptKey, 5, TimeUnit.MINUTES);

        if (attempts > 5) {
            throw new InvalidOtpException("Too many attempts");
        }

        String key = RedisKey.changePasswordOtpKey(userId);
        String otpHash = redisTemplate.opsForValue().get(key);
        if (otpHash == null) {
            throw new OtpExpiryException("OTP expired:");
        }
        if (!passwordEncoder.matches(request.otp(), otpHash)) {
            throw new InvalidOtpException("OTP is invalid");
        }
        redisTemplate.delete(key);
        redisTemplate.delete(attemptKey);
        String resetToken = jWTService.generatePasswordResetToken(userId);

        return new ResetTokenResponse(resetToken);
    }

    // ────────────────────── reset password ────────────────────────────────────────────────


    @Transactional
    public void resetPassword(ResetPasswordRequest request) {

        Claims claims = jWTService.extractClaims(request.resetToken());

        String purpose = claims.get("purpose", String.class);

        if (!"PASSWORD_RESET".equals(purpose)) {
            throw new InvalidPasswordException("Invalid reset token.");
        }

        Long userId = Long.valueOf(claims.getSubject());

        User user = getUser(userId);

        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);

        if(request.logoutFromAllDevices()){
            userSessionService.revokeAllSessions(user.getId());// logged out from all device
        }
    }

    // ────────────────────── Helper ────────────────────────────────────────────────


    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
