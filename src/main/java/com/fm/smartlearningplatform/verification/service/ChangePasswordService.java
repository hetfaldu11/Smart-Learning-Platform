package com.fm.smartlearningplatform.verification.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.*;
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
import com.fm.smartlearningplatform.util.OtpGenerator;
import com.fm.smartlearningplatform.verification.dto.request.ChangePasswordRequest;
import com.fm.smartlearningplatform.verification.dto.request.PasswordResetRequest;
import com.fm.smartlearningplatform.verification.dto.request.ResetPasswordRequest;
import com.fm.smartlearningplatform.verification.dto.request.VerifyResetOtpRequest;
import com.fm.smartlearningplatform.verification.dto.response.ResetTokenResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
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
    private final StringRedisTemplate redisTemplate;

    private static final int OTP_EXPIRY_SECONDS = 300;
    private static final int OTP_RESEND_SECONDS = 30;


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

    // ────────────────────── send password OTP  ────────────────────────────────────────────────

    @Transactional
    public void sendPasswordResetOtp(PasswordResetRequest request)
    {

        Long userId= userRepository.findByEmailAndDeletedAtIsNull(request.email()).orElseThrow(()->new ResourceNotFoundException("user Not Found")).getId();
        log.info("Password reset OTP requested for userId: {}", userId);

        rateLimitService.consume(RateLimitType.FORGOT_PASSWORD, userId.toString());

        String coolDownKey= "otp:coolDown:password"+userId;

        if(Boolean.TRUE.equals(redisTemplate.hasKey(coolDownKey)))
        {
            throw new OtpWaitException("wait for send another OTP:");
        }

        String otp = OtpGenerator.generate();

        String key= "otp:password:"+ userId;

        redisTemplate.opsForValue().set(
                key, passwordEncoder.encode(otp),
                OTP_EXPIRY_SECONDS,
                TimeUnit.SECONDS
        );

        redisTemplate.opsForValue().set(
                coolDownKey,"blocked",
                OTP_RESEND_SECONDS,
                TimeUnit.SECONDS
        );


        emailService.sendOtp(request.email(), otp);
    }

    // ────────────────────── verify otp ────────────────────────────────────────────────


    @Transactional
    public ResetTokenResponse verifyPasswordResetOtp(VerifyResetOtpRequest request)
    {
        Long userId= userRepository.findByEmailAndDeletedAtIsNull(request.email()).orElseThrow(()->new ResourceNotFoundException("user Not Found")).getId();

        String attemptKey = "otp:attempt:password:" + userId;

        Long attempts = redisTemplate.opsForValue().increment(attemptKey);

        redisTemplate.expire(attemptKey, 5, TimeUnit.MINUTES);

        if(attempts > 5) {
            throw new InvalidOtpException("Too many attempts");
        }


        String key = "otp:password:"+ userId;
        String otpHash= redisTemplate.opsForValue().get(key);
        if(otpHash==null)
        {
            throw new OtpExpiryException("OTP expired:");
        }
        if(!passwordEncoder.matches(request.otp(),otpHash))
        {
            throw  new InvalidOtpException("OTP is invalid");
        }
        redisTemplate.delete(key);
        redisTemplate.delete(attemptKey);
        String resetToken = jWTService.generatePasswordResetToken(userId);

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
}
