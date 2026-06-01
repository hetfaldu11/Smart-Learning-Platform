package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.role.request.CreateRoleRequest;
import com.fm.smartlearningplatform.user.dto.role.request.UpdateRoleRequest;
import com.fm.smartlearningplatform.user.dto.role.response.RoleResponse;
import com.fm.smartlearningplatform.user.mapper.RoleMapper;
import com.fm.smartlearningplatform.user.model.Role;
import com.fm.smartlearningplatform.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public RoleResponse createRole(CreateRoleRequest request) {
        if (roleRepository.existsByNameAndDeletedAtIsNull(request.name()))
            throw new DuplicateResourceException("Role already exists.");

        return roleMapper.toResponse(
                roleRepository.save(
                        roleMapper.toEntity(request)
                )
        );
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public RoleResponse updateRole(Long id, UpdateRoleRequest request) {
        Role role = roleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found."));

        if (roleRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("Role already exists.");

        roleMapper.updateRoleFromRequest(request, role);

        return roleMapper.toResponse(roleRepository.save(role));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean existsByIdAndDeletedAtIsNull(Long id) {
        return roleRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public RoleResponse findByIdAndDeletedAtIsNull(Long id) {
        return roleMapper.toResponse(
                roleRepository.findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Role not exists.")));
    }

    @Transactional(readOnly = true)
    public boolean existsByNameAndDeletedAtIsNull(String name) {
        return roleRepository.existsByNameAndDeletedAtIsNull(name);
    }

    @Transactional(readOnly = true)
    public RoleResponse findByNameAndDeletedAtIsNull(String name) {
        return roleMapper.toResponse(
                roleRepository.findByNameAndDeletedAtIsNull(name)
                        .orElseThrow(() -> new ResourceNotFoundException("Role not exists."))
        );
    }

    @Transactional(readOnly = true)
    public List<RoleResponse> findAllActive() {
        return roleRepository.findByDeletedAtIsNull()
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id) {
        Role role = roleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not exist."));

        role.setDeletedAt(LocalDateTime.now());

        roleRepository.save(role);
    }
}
