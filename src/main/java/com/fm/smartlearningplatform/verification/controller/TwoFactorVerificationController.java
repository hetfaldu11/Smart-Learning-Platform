package com.fm.smartlearningplatform.verification.controller;

import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.verification.dto.request.EnableTwoFactorRequest;
import com.fm.smartlearningplatform.verification.service.TwoFactorVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/two-factor")
@RequiredArgsConstructor
public class TwoFactorVerificationController {

    private final TwoFactorVerificationService twoFactorVerificationService;

    @PostMapping("/enable")
    public ResponseEntity<Void> enable(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody EnableTwoFactorRequest request) {
        twoFactorVerificationService.validateTwoFactorNotVerified(userPrincipal.id());
        twoFactorVerificationService.enableTwoFactor(userPrincipal.id(), request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/disable")
    public ResponseEntity<Void> disable(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody EnableTwoFactorRequest request) {
        twoFactorVerificationService.disableTwoFactor(userPrincipal.id(), request);
        return ResponseEntity.ok().build();
    }
}