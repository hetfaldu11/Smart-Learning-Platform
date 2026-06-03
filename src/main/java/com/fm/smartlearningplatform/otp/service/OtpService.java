package com.fm.smartlearningplatform.otp.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.InvalidOtpException;
import com.fm.smartlearningplatform.exceptionhandler.exception.OtpExpiryException;
import com.fm.smartlearningplatform.exceptionhandler.exception.OtpWaitException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.otp.model.OtpType;
import com.fm.smartlearningplatform.otp.model.UserOtp;
import com.fm.smartlearningplatform.otp.repository.UserOtpRepository;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import com.fm.smartlearningplatform.util.OtpGenerator;
import com.fm.smartlearningplatform.verification.dto.request.PasswordResetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class OtpService {

    private final UserRepository userRepository;

    private final UserOtpRepository userOtpRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    @Transactional

    public void sendEmailVerificationOtp(Long userId, int expirySeconds, int resendOtpSeconds) {

        User user = getUser(userId);

        validateLastEmailOtp(userId, expirySeconds,resendOtpSeconds);

        String otp = OtpGenerator.generate();

        UserOtp userOtp = UserOtp.builder()
                .user(user)
                .type(OtpType.EMAIL_VERIFICATION)
                .otpHash(passwordEncoder.encode(otp))
                .expiresAt(LocalDateTime.now().plusSeconds(expirySeconds))
                .build();

        userOtpRepository.save(userOtp);

        emailService.sendOtp(user.getEmail(), otp);

        System.out.println(otp);

        emailService.sendOtp(user.getEmail(),otp);
    }

    @Transactional
    public void verifyEmailOtp(Long userId, String otp) {

        UserOtp userOtp = getEmailOtp(userId);
        validateOtp(otp, userOtp);

        userOtp.setUsed(true);
        userOtp.setVerifiedAt(LocalDateTime.now());

        userOtpRepository.save(userOtp);
    }

    @Transactional
    public void sendPhoneVerificationOtp(Long userId, int expirySeconds, int resendOtpSeconds) {
        User user = getUser(userId);

        validateLastPhoneOtp(userId,expirySeconds, resendOtpSeconds);

        String otp = OtpGenerator.generate();

        UserOtp userOtp = UserOtp.builder()
                .user(user)
                .type(OtpType.PHONE_VERIFICATION)
                .otpHash(passwordEncoder.encode(otp))
                .expiresAt(LocalDateTime.now().plusSeconds(expirySeconds))
                .build();

        userOtpRepository.save(userOtp);

        System.out.println(otp);

        emailService.sendOtp(user.getEmail(),otp);
    }

    @Transactional
    public void verifyPhoneOtp(Long userId, String otp) {

        UserOtp userOtp = getPhoneOtp(userId);

        validateOtp(otp, userOtp);

        userOtp.setUsed(true);
        userOtp.setVerifiedAt(LocalDateTime.now());

        userOtpRepository.save(userOtp);
    }


    @Transactional
    public void sendPasswordResetOtp(PasswordResetRequest request)
    {
        User user = getUser(request.userId());

        String otp = OtpGenerator.generate();

        UserOtp userOtp = UserOtp.builder()
                .user(user)
                .type(OtpType.PASSWORD_RESET)
                .otpHash(passwordEncoder.encode(otp))
                .expiresAt(LocalDateTime.now().plusSeconds(request.expirySeconds()))
                .build();

        userOtpRepository.save(userOtp);

        emailService.sendOtp(user.getEmail(), otp);
    }

    // Helper

    private void validateOtp(String otp, UserOtp userOtp) {
        if (userOtp.isUsed() || userOtp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new OtpExpiryException("Otp is expired.");
        }
        if (!passwordEncoder.matches(otp, userOtp.getOtpHash())) {
            throw new InvalidOtpException("Otp is invalid.");
        }
    }

    private void validateLastEmailOtp(Long id, int expirySeconds, int resendOtpSeconds){
        UserOtp userOtp = userOtpRepository.findTopByUserIdAndTypeAndUsedFalseOrderByIdDesc(id, OtpType.EMAIL_VERIFICATION)
                .orElse(null);

        if(userOtp == null) return;

        LocalDateTime resendAllowedAt =
                userOtp.getExpiresAt()
                        .minusSeconds(expirySeconds)
                        .plusSeconds(resendOtpSeconds);

        if (LocalDateTime.now().isBefore(resendAllowedAt)) {
            long waitSeconds = Duration.between(
                    LocalDateTime.now(),
                    resendAllowedAt
            ).toSeconds();

            throw new OtpWaitException(
                    "Wait for " + waitSeconds + " seconds."
            );
        }
    }

    private void validateLastPhoneOtp(Long id, int expirySeconds, int resendOtpSeconds){
        UserOtp userOtp = userOtpRepository.findTopByUserIdAndTypeAndUsedFalseOrderByIdDesc(id, OtpType.PHONE_VERIFICATION)
                .orElse(null);

        if(userOtp == null) return;

        LocalDateTime resendAllowedAt =
                userOtp.getExpiresAt()
                        .minusSeconds(expirySeconds)
                        .plusSeconds(resendOtpSeconds);

        if (LocalDateTime.now().isBefore(resendAllowedAt)) {
            long waitSeconds = Duration.between(
                    LocalDateTime.now(),
                    resendAllowedAt
            ).toSeconds();

            throw new OtpWaitException(
                    "Wait for " + waitSeconds + " seconds."
            );
        }
    }



    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    private UserOtp getEmailOtp(Long id) {
        return userOtpRepository.findTopByUserIdAndTypeAndUsedFalseOrderByIdDesc(id, OtpType.EMAIL_VERIFICATION)
                .orElseThrow(() -> new ResourceNotFoundException("Otp not found."));
    }

    private UserOtp getPhoneOtp(Long id) {
        return userOtpRepository.findTopByUserIdAndTypeAndUsedFalseOrderByIdDesc(id, OtpType.PHONE_VERIFICATION)
                .orElseThrow(() -> new ResourceNotFoundException("Otp not found."));
    }
}