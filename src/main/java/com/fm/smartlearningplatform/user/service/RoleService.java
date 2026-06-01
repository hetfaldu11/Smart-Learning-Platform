package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.role.request.CreateRoleRequest;
import com.fm.smartlearningplatform.user.dto.role.request.UpdateRoleRequest;
import com.fm.smartlearningplatform.user.dto.role.response.DeleteRoleResponse;
import com.fm.smartlearningplatform.user.dto.role.response.RoleResponse;
import com.fm.smartlearningplatform.user.mapper.RoleMapper;
import com.fm.smartlearningplatform.user.model.Role;
import com.fm.smartlearningplatform.user.repository.RoleRepository;
import com.fm.smartlearningplatform.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleMapper roleMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public RoleResponse create(CreateRoleRequest request) {
        validateRoleNotExist(request.name());
        return roleMapper.toResponse(roleRepository.save(roleMapper.toEntity(request)));
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public RoleResponse update(Long id, UpdateRoleRequest request) {

        Role role = getRole(id);

        if (roleRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("Role already exist.");

        roleMapper.updateRoleFromRequest(request, role);
        return roleMapper.toResponse(roleRepository.save(role));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean foundById(Long id) {
        return roleRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public RoleResponse findById(Long id) {
        return roleMapper.toResponse(getRole(id));
    }

    @Transactional(readOnly = true)
    public List<RoleResponse> searchByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }

        keyword = keyword.trim();
        return roleRepository.findByDeletedAtIsNullAndNameContainingIgnoreCase(keyword)
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    private List<RoleResponse> findAll() {
        return roleRepository.findByDeletedAtIsNullOrderByNameAsc()
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    // ─── Delete ────────────────────────────────────────────────

    @Transactional
    public DeleteRoleResponse deleteById(Long id) {
        Role role = getRole(id);
        userRoleRepository.deleteByRoleId(id);

        role.setDeletedAt(LocalDateTime.now());

        roleRepository.save(role);

        return new DeleteRoleResponse("Role is deleted successfully.");
    }

    // ─── Helper ────────────────────────────────────────────────

    private void validateRoleNotExist(String name) {
        if (roleRepository.existsByNameAndDeletedAtIsNull(name))
            throw new DuplicateResourceException("Role already exist.");
    }

    private Role getRole(Long id) {
        return roleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found."));
    }
}
