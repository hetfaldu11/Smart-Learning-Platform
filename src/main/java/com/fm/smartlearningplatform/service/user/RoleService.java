package com.fm.smartlearningplatform.service.user;
import com.fm.smartlearningplatform.dto.user.role.request.CreateRoleRequest;
import com.fm.smartlearningplatform.dto.user.role.request.UpdateRoleRequest;
import com.fm.smartlearningplatform.dto.user.role.response.RoleResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.RoleMapper;
import com.fm.smartlearningplatform.model.user.Role;
import com.fm.smartlearningplatform.repository.user.RoleRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        if (roleRepository.existsByNameAndDeletedAtIsNull(request.getName()))
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

        if (roleRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.getName()))
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
    public RoleResponse findByIdAndDeletedAtIsNull(Long id){
        return roleMapper.toResponse(
                roleRepository.findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Role not exists.")));
    }

    @Transactional(readOnly = true)
    public boolean existsByNameAndDeletedAtIsNull(String name){
        return roleRepository.existsByNameAndDeletedAtIsNull(name);
    }

    @Transactional(readOnly = true)
    public RoleResponse findByNameAndDeletedAtIsNull(String name){
        return roleMapper.toResponse(
                roleRepository.findByNameAndDeletedAtIsNull(name)
                        .orElseThrow(()->new ResourceNotFoundException("Role not exists."))
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
    public void deleteById(Long id){
        Role role = roleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not exist."));

        role.setDeletedAt(LocalDateTime.now());

        roleRepository.save(role);
    }
}
