package com.fm.smartlearningplatform.verification.controller;

import com.fm.smartlearningplatform.otp.dto.request.CreateEmailRequest;
import com.fm.smartlearningplatform.otp.dto.request.VerifyEmailRequest;
import com.fm.smartlearningplatform.otp.service.OtpService;
import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.verification.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/email")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final OtpService otpService;
    private final EmailVerificationService emailVerificationService;

    @PostMapping("/request")
    public ResponseEntity<String> requestEmailVerification(@AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.id();
        String email= principal.email();

        emailVerificationService.validateEmailNotVerified(userId);

        otpService.sendEmailVerificationOtp(userId,email);

        return ResponseEntity.ok().body("Otp sent successfully.");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmail(@AuthenticationPrincipal UserPrincipal principal, @RequestBody VerifyEmailRequest request) {
        Long userId = principal.id();

        otpService.verifyEmailOtp(userId, request.otp());

        emailVerificationService.verifyEmail(userId);

        return ResponseEntity.ok().body("Otp verified.");
    }
}