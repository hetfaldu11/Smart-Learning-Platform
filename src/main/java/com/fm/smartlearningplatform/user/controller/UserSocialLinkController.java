package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.user.dto.userSocialLink.request.CreateUserSocialLinkRequest;
import com.fm.smartlearningplatform.user.dto.userSocialLink.request.PatchUserSocialLinkRequest;
import com.fm.smartlearningplatform.user.dto.userSocialLink.response.DeleteUserSocialLinkResponse;
import com.fm.smartlearningplatform.user.dto.userSocialLink.response.UserSocialLinkResponse;
import com.fm.smartlearningplatform.user.service.UserSocialLinkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/social-links")
@RequiredArgsConstructor
public class UserSocialLinkController {

    private final UserSocialLinkService userSocialLinkService;

    @PostMapping
    public ResponseEntity<UserSocialLinkResponse> create(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody CreateUserSocialLinkRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userSocialLinkService.create(principal.id(), request));
    }

    @GetMapping
    public ResponseEntity<List<UserSocialLinkResponse>> getAll(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(userSocialLinkService.findAll(principal.id()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSocialLinkResponse> getById(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long id) {
        return ResponseEntity.ok(userSocialLinkService.findById(principal.id(), id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserSocialLinkResponse> update(@AuthenticationPrincipal UserPrincipal principal,@PathVariable Long id ,@Valid @RequestBody PatchUserSocialLinkRequest request) {
        return ResponseEntity.ok(userSocialLinkService.update(principal.id(), id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteUserSocialLinkResponse> delete(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long id) {
        return ResponseEntity.ok(userSocialLinkService.deleteById(principal.id(), id));
    }
}