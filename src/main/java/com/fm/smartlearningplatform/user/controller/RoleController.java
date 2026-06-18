package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.user.dto.role.request.CreateRoleRequest;
import com.fm.smartlearningplatform.user.dto.role.request.UpdateRoleRequest;
import com.fm.smartlearningplatform.user.dto.role.response.DeleteRoleResponse;
import com.fm.smartlearningplatform.user.dto.role.response.RoleResponse;
import com.fm.smartlearningplatform.user.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody CreateRoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<RoleResponse>> getRoles(@RequestParam(value = "q", required = false) String keyword,
                                                       @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(roleService.searchByKeyword(keyword, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRoleById(@PathVariable Long id, @Valid @RequestBody UpdateRoleRequest request) {
        return ResponseEntity.ok(roleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteRoleResponse> deleteRoleById(@PathVariable Long id) {
        return ResponseEntity.ok().body(roleService.deleteById(id));
    }
}