package com.fm.smartlearningplatform.controller.user;

import com.fm.smartlearningplatform.dto.user.role.request.CreateRoleRequest;
import com.fm.smartlearningplatform.dto.user.role.request.UpdateRoleRequest;
import com.fm.smartlearningplatform.dto.user.role.response.RoleResponse;
import com.fm.smartlearningplatform.model.user.Role;
import com.fm.smartlearningplatform.service.user.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.findByIdAndDeletedAtIsNull(id));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAllActive());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @Valid @RequestBody UpdateRoleRequest request) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}