package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.user.dto.userPreference.request.CreateUserPreferenceRequest;
import com.fm.smartlearningplatform.user.dto.userPreference.request.PatchUserPreferenceRequest;
import com.fm.smartlearningplatform.user.dto.userPreference.response.UserPreferenceResponse;
import com.fm.smartlearningplatform.user.service.UserPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/preferences")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    @PostMapping
    public ResponseEntity<UserPreferenceResponse> create(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody CreateUserPreferenceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userPreferenceService.create(principal.id(), request));
    }

    @GetMapping
    public ResponseEntity<UserPreferenceResponse> get(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(userPreferenceService.findByUserId(principal.id()));
    }

    @PatchMapping
    public ResponseEntity<UserPreferenceResponse> patch(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody PatchUserPreferenceRequest request) {
        return ResponseEntity.ok(userPreferenceService.update(principal.id(), request));
    }
}
