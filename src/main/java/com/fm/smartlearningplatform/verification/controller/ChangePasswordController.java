package com.fm.smartlearningplatform.verification.controller;

import com.fm.smartlearningplatform.otp.service.OtpService;
import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.user.service.UserService;
import com.fm.smartlearningplatform.verification.dto.request.ChangePasswordRequest;
import com.fm.smartlearningplatform.verification.dto.request.PasswordResetRequest;
import com.fm.smartlearningplatform.verification.dto.request.ResetPasswordRequest;
import com.fm.smartlearningplatform.verification.dto.request.VerifyResetOtpRequest;
import com.fm.smartlearningplatform.verification.dto.response.ResetTokenResponse;
import com.fm.smartlearningplatform.verification.service.ChangePasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class ChangePasswordController {

    private final UserService userService;
    private final ChangePasswordService changePasswordService;
    private final OtpService otpService;

    @PatchMapping("/change-password")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal UserPrincipal principal,
                                               @RequestBody @Valid ChangePasswordRequest request) {
        changePasswordService.changePassword(principal.id(), request);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/send-otp")
    public ResponseEntity<Void> sendOtp(@RequestBody @Valid PasswordResetRequest request) {
        changePasswordService.sendPasswordResetOtp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ResetTokenResponse> verifyOtp(@RequestBody @Valid VerifyResetOtpRequest request) {
        return ResponseEntity.ok(changePasswordService.verifyPasswordResetOtp(request));
    }


    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        changePasswordService.resetPassword(request);
        return ResponseEntity.noContent().build();
    }

}
