package com.fm.smartlearningplatform.otp.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.InvalidOtpException;
import com.fm.smartlearningplatform.exceptionhandler.exception.OtpExpiryException;
import com.fm.smartlearningplatform.exceptionhandler.exception.OtpWaitException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.otp.model.OtpType;
import com.fm.smartlearningplatform.otp.model.UserOtp;
import com.fm.smartlearningplatform.otp.repository.UserOtpRepository;
import com.fm.smartlearningplatform.security.ratelimit.RateLimitService;
import com.fm.smartlearningplatform.security.ratelimit.RateLimitType;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import com.fm.smartlearningplatform.util.OtpGenerator;
import com.fm.smartlearningplatform.verification.dto.request.PasswordResetRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OtpService {

    private final UserRepository userRepository;

    private final UserOtpRepository userOtpRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final RateLimitService rateLimitService;

    private final StringRedisTemplate redisTemplate;


    private static final int OTP_EXPIRY_SECONDS = 300;
    private static final int OTP_RESEND_SECONDS = 30;



    // ──────────────────────  send email otp  ────────────────────────────────────────────────

    @Transactional
    public void sendEmailVerificationOtp(Long userId, String email) {

        String cooldownKey = "otp:cooldown:email:" + userId;

        if(Boolean.TRUE.equals(redisTemplate.hasKey(cooldownKey))) {
            throw new OtpWaitException("Wait before requesting another OTP.");
        }

        String otp = OtpGenerator.generate();

        String otpKey = "otp:email:" + userId;

        redisTemplate.opsForValue().set(
                otpKey,
                passwordEncoder.encode(otp),
                OTP_EXPIRY_SECONDS,
                TimeUnit.SECONDS
        );

        redisTemplate.opsForValue().set(
                cooldownKey,
                "blocked",
                OTP_RESEND_SECONDS,
                TimeUnit.SECONDS
        );

        emailService.sendOtp(email, otp);
    }

    // ──────────────────────  verify email otp  ────────────────────────────────────────────────


    @Transactional
    public void verifyEmailOtp(Long userId, String otp) {


        String attemptKey = "otp:attempt:email:" + userId;

        Long attempts = redisTemplate.opsForValue().increment(attemptKey);

        redisTemplate.expire(attemptKey, 5, TimeUnit.MINUTES);

        if(attempts > 5) {
            throw new InvalidOtpException("Too many attempts");
        }


        String key = "otp:email:" + userId;
        String otpHash= redisTemplate.opsForValue().get(key);
        if(otpHash==null)
        {
            throw new OtpExpiryException("OTP expired:");
        }
        if(!passwordEncoder.matches(otp,otpHash))
        {
            throw  new InvalidOtpException("OTP is invalid");
        }
        redisTemplate.delete(key);
        redisTemplate.delete(attemptKey);

        log.info("Email OTP verified successfully for userId: {}", userId);
    }

    // ──────────────────────send phone otp ────────────────────────────────────────────────

    public void sendPhoneVerificationOtp(Long userId, String email) {

        log.info("Sending phone verification OTP for userId: {}", userId);

        String coolDownKey = "otp:cooldown:phone:"+ userId;

        if(Boolean.TRUE.equals(redisTemplate.hasKey(coolDownKey)))
        {
            throw  new OtpWaitException("wait before requesting another OTP:");
        }

        String phoneKey = "otp:phone:"+ userId;

        String otp = OtpGenerator.generate();

        redisTemplate.opsForValue().set(
                phoneKey, passwordEncoder.encode(otp),
                OTP_EXPIRY_SECONDS,
                TimeUnit.SECONDS
        );

        redisTemplate.opsForValue().set(
                coolDownKey, "blocked",
                OTP_RESEND_SECONDS ,
                TimeUnit.SECONDS
        );


        System.out.println(otp);

        emailService.sendOtp(email,otp);
    }

    // ──────────────────────verify phone otp ────────────────────────────────────────────────


    @Transactional
    public void verifyPhoneOtp(Long userId, String otp)
    {

        String attemptKey = "otp:attempt:phone:" + userId;

        Long attempts = redisTemplate.opsForValue().increment(attemptKey);

        redisTemplate.expire(attemptKey, 5, TimeUnit.MINUTES);

        if(attempts > 5) {
            throw new InvalidOtpException("Too many attempts");
        }

        String phoneKey = "otp:phone:"+ userId;

        String otpHash= redisTemplate.opsForValue().get(phoneKey);

        if(otpHash==null)
        {
            throw  new OtpExpiryException("OTP expired:");
        }
        if(!passwordEncoder.matches(otp, otpHash))
        {
            throw  new InvalidOtpException("OTP is invalid ");
        }

        redisTemplate.delete(phoneKey);

        redisTemplate.delete(attemptKey);

        log.info("Phone OTP verified successfully for userId: {}", userId);
    }

}