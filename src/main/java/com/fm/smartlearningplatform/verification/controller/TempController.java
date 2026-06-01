package com.fm.smartlearningplatform.verification.controller;

import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.verification.dto.response.UserVerificationResponse;
import com.fm.smartlearningplatform.verification.service.TempService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/verification")
@RequiredArgsConstructor
public class TempController {

    private final TempService tempService;

    @GetMapping
    public ResponseEntity<UserVerificationResponse> findById(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.ok().body(tempService.findById(userPrincipal.id()));
    }

}