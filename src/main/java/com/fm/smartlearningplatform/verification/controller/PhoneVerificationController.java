package com.fm.smartlearningplatform.verification.controller;


import com.fm.smartlearningplatform.otp.dto.request.CreatePhoneRequest;
import com.fm.smartlearningplatform.otp.dto.request.VerifyPhoneRequest;
import com.fm.smartlearningplatform.otp.service.OtpService;
import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.verification.dto.response.UserVerificationResponse;
import com.fm.smartlearningplatform.verification.service.PhoneVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/phone")
@RequiredArgsConstructor
public class PhoneVerificationController {

    private final OtpService otpService;
    private final PhoneVerificationService phoneVerificationService;

    @PostMapping("/request")
    public ResponseEntity<String> requestPhoneVerification(@AuthenticationPrincipal UserPrincipal principal, @RequestBody CreatePhoneRequest request) {
        Long userId = principal.id();
        phoneVerificationService.validatePhoneNumberNotVerified(userId);
        otpService.sendPhoneVerificationOtp(userId, request.expiryMinute());
        return ResponseEntity.ok().body("Otp sent successfully.");
    }

    @PostMapping("/verify")
    public ResponseEntity<UserVerificationResponse> verifyPhone(@AuthenticationPrincipal UserPrincipal principal, @RequestBody VerifyPhoneRequest request) {
        Long userId = principal.id();
        otpService.verifyPhoneOtp(userId, request.otp());
        return ResponseEntity.ok().body(phoneVerificationService.verifyPhone(userId));
    }
}