package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.user.dto.user.request.CreateUserRequest;
import com.fm.smartlearningplatform.user.dto.user.request.PatchUserRequest;
import com.fm.smartlearningplatform.user.dto.user.response.UserResponse;
import com.fm.smartlearningplatform.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }

    // ─── Find By Id ───────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    // ─── Find All ─────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<Page<UserResponse>> findAll(@PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }
    // ─── Update ───────────────────────────────────────────────

    @PatchMapping
    public ResponseEntity<UserResponse> update(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody PatchUserRequest request) {
        return ResponseEntity.ok(userService.update(principal.id(), request));
    }

    // ─── Soft Delete ──────────────────────────────────────────

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserPrincipal principal) {
        userService.delete(principal.id());
        return ResponseEntity.noContent().build();
    }

    // ─── Restore ──────────────────────────────────────────────

//    @PatchMapping("/restore")
//    public ResponseEntity<UserResponse> restore(@PathVariable Long id) {
//        return ResponseEntity.ok(userService.restore(id));
//    }

    // ─── Current User ─────────────────────────────────────────

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(userService.findById(principal.id()));
    }
}