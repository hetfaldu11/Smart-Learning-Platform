package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.user.dto.userInterest.request.CreateUserInterestRequest;
import com.fm.smartlearningplatform.user.dto.userInterest.response.DeleteUserInterestResponse;
import com.fm.smartlearningplatform.user.dto.userInterest.response.UserInterestResponse;
import com.fm.smartlearningplatform.user.service.UserInterestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-interest")
@RequiredArgsConstructor
public class UserInterestController {

    private final UserInterestService userInterestService;

    @PostMapping
    public ResponseEntity<UserInterestResponse> createUserInterest(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody CreateUserInterestRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userInterestService.create(principal.id(), request));
    }

    @GetMapping
    public ResponseEntity<List<UserInterestResponse>> findInterests(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userInterestService.findByUserId(userPrincipal.id()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteUserInterestResponse> deleteById(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long id) {
        return ResponseEntity.ok().body(userInterestService.deleteById(principal.id(), id));
    }
}