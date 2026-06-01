package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.user.dto.roleAuthority.request.CreateRoleAuthorityRequest;
import com.fm.smartlearningplatform.user.dto.roleAuthority.request.DeleteRoleAuthorityRequest;
import com.fm.smartlearningplatform.user.dto.roleAuthority.response.DeleteRoleAuthorityResponse;
import com.fm.smartlearningplatform.user.dto.roleAuthority.response.RoleAuthorityResponse;
import com.fm.smartlearningplatform.user.service.RoleAuthorityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-authority")
@RequiredArgsConstructor
public class RoleAuthorityController {

    private final RoleAuthorityService roleAuthorityService;

    @PostMapping
    public ResponseEntity<RoleAuthorityResponse> createRoleAuthority( @Valid @RequestBody CreateRoleAuthorityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleAuthorityService.create( request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<RoleAuthorityResponse>> findAuthorities(@PathVariable Long id) {
        return ResponseEntity.ok(roleAuthorityService.findByRoleId(id));
    }

    @DeleteMapping()
    public ResponseEntity<DeleteRoleAuthorityResponse> deleteById(@Valid @RequestBody DeleteRoleAuthorityRequest request) {
        return ResponseEntity.ok().body(roleAuthorityService.deleteById(request));
    }
}