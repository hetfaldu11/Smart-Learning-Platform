package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.user.dto.userProfile.request.CreateUserProfileRequest;
import com.fm.smartlearningplatform.user.dto.userProfile.request.PatchUserProfileRequest;
import com.fm.smartlearningplatform.user.dto.userProfile.response.UserProfileResponse;
import com.fm.smartlearningplatform.user.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfileResponse> createProfile(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody CreateUserProfileRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileService.create(principal.id(), request));
    }

    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(userProfileService.findByUserId(principal.id()));
    }

    @PatchMapping
    public ResponseEntity<UserProfileResponse> updateProfile(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody PatchUserProfileRequest request) {
        return ResponseEntity.ok(userProfileService.update(principal.id(), request));
    }
}