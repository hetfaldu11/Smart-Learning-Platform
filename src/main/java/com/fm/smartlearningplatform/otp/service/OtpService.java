package com.fm.smartlearningplatform.otp.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.InvalidOtpException;
import com.fm.smartlearningplatform.exceptionhandler.exception.OtpExpiryException;
import com.fm.smartlearningplatform.exceptionhandler.exception.OtpWaitException;
import com.fm.smartlearningplatform.otp.repository.UserOtpRepository;
import com.fm.smartlearningplatform.security.ratelimit.RateLimitService;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import com.fm.smartlearningplatform.util.OtpGenerator;
import com.fm.smartlearningplatform.util.RedisKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OtpService {

    private final UserRepository userRepository;

    private final UserOtpRepository userOtpRepository;

    private final PasswordEncoder passwordEncoder;

    private final OtpSendService otpSendService;

    private final RateLimitService rateLimitService;

    private final StringRedisTemplate redisTemplate;

    @Value("${otp.email.expiry.time.seconds}")
    private int EMAIL_OTP_EXPIRY_SECONDS;

    @Value("${otp.email.resend.seconds}")
    private int EMAIL_OTP_RESEND_SECONDS;

    @Value("${otp.phone.expiry.time.seconds}")
    private int PHONE_OTP_EXPIRY_SECONDS;

    @Value("${otp.phone.resend.seconds}")
    private int PHONE_OTP_RESEND_SECONDS;


    // ──────────────────────  send email otp  ────────────────────────────────────────────────

    @Transactional
    public void sendEmailVerificationOtp(Long userId, String email) {

        String cooldownKey = RedisKey.emailOtpCooldownKey(userId);

        if (Boolean.TRUE.equals(redisTemplate.hasKey(cooldownKey))) {
            throw new OtpWaitException("Wait before requesting another OTP.");
        }

        String otp = OtpGenerator.generate();

        String otpKey = RedisKey.emailOtpKey(userId);

        redisTemplate.opsForValue().set(
                otpKey,
                passwordEncoder.encode(otp),
                EMAIL_OTP_EXPIRY_SECONDS,
                TimeUnit.SECONDS
        );

        redisTemplate.opsForValue().set(
                cooldownKey,
                "block",
                EMAIL_OTP_RESEND_SECONDS,
                TimeUnit.SECONDS
        );

        otpSendService.sendEmailVerificationOtp(email, otp);
    }

    // ──────────────────────  verify email otp  ────────────────────────────────────────────────


    @Transactional
    public void verifyEmailOtp(Long userId, String otp) {

        String attemptKey = RedisKey.emailOtpAttemptKey(userId);

        Long attempts = redisTemplate.opsForValue().increment(attemptKey);

        redisTemplate.expire(attemptKey, 5, TimeUnit.MINUTES);

        if (attempts > 5) {
            throw new InvalidOtpException("Too many attempts");
        }


        String key = RedisKey.emailOtpKey(userId);
        String otpHash = redisTemplate.opsForValue().get(key);
        if (otpHash == null) {
            throw new OtpExpiryException("OTP expired:");
        }
        if (!passwordEncoder.matches(otp, otpHash)) {
            throw new InvalidOtpException("OTP is invalid");
        }
        redisTemplate.delete(key);
        redisTemplate.delete(attemptKey);

        log.info("Email OTP verified successfully for userId: {}", userId);
    }

    // ──────────────────────send phone otp ────────────────────────────────────────────────

    public void sendPhoneVerificationOtp(Long userId, String email) {

        log.info("Sending phone verification OTP for userId: {}", userId);

        String coolDownKey = RedisKey.phoneOtpCooldownKey(userId);

        if (Boolean.TRUE.equals(redisTemplate.hasKey(coolDownKey))) {
            throw new OtpWaitException("wait before requesting another OTP:");
        }

        String phoneKey = RedisKey.phoneOtpKey(userId);

        String otp = OtpGenerator.generate();

        redisTemplate.opsForValue().set(
                phoneKey, passwordEncoder.encode(otp),
                PHONE_OTP_EXPIRY_SECONDS,
                TimeUnit.SECONDS
        );

        redisTemplate.opsForValue().set(
                coolDownKey, "blocked",
                PHONE_OTP_RESEND_SECONDS,
                TimeUnit.SECONDS
        );


        System.out.println(otp);

        otpSendService.sendPhoneVerificationOtp(email, otp);
    }

    // ──────────────────────verify phone otp ────────────────────────────────────────────────


    @Transactional
    public void verifyPhoneOtp(Long userId, String otp) {

        String attemptKey = RedisKey.phoneOtpAttemptKey(userId);

        Long attempts = redisTemplate.opsForValue().increment(attemptKey);

        redisTemplate.expire(attemptKey, 5, TimeUnit.MINUTES);

        if (attempts > 5) {
            throw new InvalidOtpException("Too many attempts");
        }

        String phoneKey = RedisKey.phoneOtpKey(userId);

        String otpHash = redisTemplate.opsForValue().get(phoneKey);

        if (otpHash == null) {
            throw new OtpExpiryException("OTP expired:");
        }
        if (!passwordEncoder.matches(otp, otpHash)) {
            throw new InvalidOtpException("OTP is invalid ");
        }

        redisTemplate.delete(phoneKey);

        redisTemplate.delete(attemptKey);

        log.info("Phone OTP verified successfully for userId: {}", userId);
    }

}