package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.user.dto.userRole.request.CreateUserRoleRequest;
import com.fm.smartlearningplatform.user.dto.userRole.response.DeleteUserRoleResponse;
import com.fm.smartlearningplatform.user.dto.userRole.response.UserRoleResponse;
import com.fm.smartlearningplatform.user.service.UserRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-role")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @PostMapping
    public ResponseEntity<UserRoleResponse> createUserRole(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody CreateUserRoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userRoleService.create(principal.id(), request));
    }

    @GetMapping
    public ResponseEntity<List<UserRoleResponse>> findRoles(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userRoleService.findByUserId(userPrincipal.id()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteUserRoleResponse> deleteById(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long id) {
        return ResponseEntity.ok().body( userRoleService.deleteById(principal.id(), id));
    }
}