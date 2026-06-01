package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.user.dto.userSkill.request.CreateUserSkillRequest;
import com.fm.smartlearningplatform.user.dto.userSkill.response.DeleteUserSkillResponse;
import com.fm.smartlearningplatform.user.dto.userSkill.response.UserSkillResponse;
import com.fm.smartlearningplatform.user.service.UserSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-skill")
@RequiredArgsConstructor
public class UserSkillController {

    private final UserSkillService userSkillService;

    @PostMapping
    public ResponseEntity<UserSkillResponse> createUserSkill(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody CreateUserSkillRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userSkillService.create(principal.id(), request));
    }

    @GetMapping
    public ResponseEntity<List<UserSkillResponse>> findSkills(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userSkillService.findByUserId(userPrincipal.id()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteUserSkillResponse> deleteById(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long id) {
        return ResponseEntity.ok().body( userSkillService.deleteById(principal.id(), id));
    }
}