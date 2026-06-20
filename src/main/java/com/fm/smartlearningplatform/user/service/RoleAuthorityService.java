package com.fm.smartlearningplatform.user.service;


import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.roleAuthority.request.CreateRoleAuthoritiesRequest;
import com.fm.smartlearningplatform.user.dto.roleAuthority.request.CreateRoleAuthorityRequest;
import com.fm.smartlearningplatform.user.dto.roleAuthority.request.DeleteRoleAuthorityRequest;
import com.fm.smartlearningplatform.user.dto.roleAuthority.response.DeleteRoleAuthorityResponse;
import com.fm.smartlearningplatform.user.dto.roleAuthority.response.RoleAuthorityResponse;
import com.fm.smartlearningplatform.user.mapper.RoleAuthorityMapper;
import com.fm.smartlearningplatform.user.model.Authority;
import com.fm.smartlearningplatform.user.model.Role;
import com.fm.smartlearningplatform.user.model.RoleAuthority;
import com.fm.smartlearningplatform.user.repository.AuthorityRepository;
import com.fm.smartlearningplatform.user.repository.RoleAuthorityRepository;
import com.fm.smartlearningplatform.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleAuthorityService {

    private final RoleAuthorityRepository roleAuthorityRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleAuthorityMapper roleAuthorityMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public RoleAuthorityResponse create(CreateRoleAuthorityRequest request) {
        Role role = getRole(request.roleId());

        Authority authority = getAuthority(request.authorityId());

        validateRoleAuthorityNotExist(request.roleId(), request.authorityId());

        RoleAuthority roleAuthority = RoleAuthority.builder()
                .role(role)
                .authority(authority)
                .build();

        return roleAuthorityMapper.toResponse(roleAuthorityRepository.save(roleAuthority));
    }

    @Transactional
    public List<RoleAuthorityResponse> create(CreateRoleAuthoritiesRequest request) {

        Set<Long> uniqueAuthorityIds = new HashSet<>(request.authorityIds());

        if (uniqueAuthorityIds.size() != request.authorityIds().size()) {
            throw new DuplicateResourceException("Duplicate authority ids are not allowed.");
        }

        Role role = getRole(request.roleId());

        List<Authority> authorities = authorityRepository.findByIdInAndDeletedAtIsNull(new ArrayList<>(uniqueAuthorityIds));

        if (authorities.size() != uniqueAuthorityIds.size()) {
            throw new ResourceNotFoundException("Some authority ids do not exist.");
        }

        Set<Long> existingAuthorityIds = roleAuthorityRepository.findAuthorityIdsByRoleId(request.roleId());

        List<RoleAuthority> roleAuthorities = new ArrayList<>(authorities.size());

        for (Authority authority : authorities) {
            if (existingAuthorityIds.contains(authority.getId())) {
                throw new DuplicateResourceException(
                        "RoleAuthority already exists."
                );
            }

            RoleAuthority roleAuthority = RoleAuthority.builder()
                    .role(role)
                    .authority(authority)
                    .build();

            roleAuthorities.add(roleAuthority);
        }

        return roleAuthorityRepository.saveAll(roleAuthorities)
                .stream()
                .map(roleAuthorityMapper::toResponse)
                .toList();
    }


    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<RoleAuthorityResponse> findByRoleId(Long roleId) {
        validateRoleExist(roleId);

        return roleAuthorityRepository.findByRoleId(roleId)
                .stream()
                .map(roleAuthorityMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RoleAuthorityResponse> findByAuthorityId(Long authorityId) {
        validateAuthorityExist(authorityId);

        return roleAuthorityRepository.findByAuthorityId(authorityId)
                .stream()
                .map(roleAuthorityMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoleAuthorityResponse findByRoleIdAndAuthorityId(Long roleId, Long authorityId) {
        validateRoleExist(roleId);

        validateAuthorityExist(authorityId);

        return roleAuthorityMapper.toResponse(getRoleAuthority(roleId, authorityId));
    }

    // ─── Delete ────────────────────────────────────────────────

    @Transactional
    public DeleteRoleAuthorityResponse deleteById(DeleteRoleAuthorityRequest request) {
        validateRoleAuthorityExist(request.roleId(), request.authorityId());
        RoleAuthority roleAuthority = getRoleAuthority(request.roleId(), request.authorityId());
        roleAuthorityRepository.delete(roleAuthority);
        return new DeleteRoleAuthorityResponse("role Authority successfully deleted:");
    }

    // ─── Helper ────────────────────────────────────────────────

    private void validateRoleAuthorityNotExist(Long roleId, Long authorityId) {
        if (roleAuthorityRepository.existsByRoleIdAndAuthorityId(roleId, authorityId))
            throw new DuplicateResourceException("RoleAuthority already exists.");
    }

    private void validateRoleAuthorityExist(Long roleId, Long authorityId) {
        if (!roleAuthorityRepository.existsByRoleIdAndAuthorityId(roleId, authorityId))
            throw new ResourceNotFoundException("RoleAuthority not found.");
    }

    private RoleAuthority getRoleAuthority(Long roleId, Long authorityId) {
        return roleAuthorityRepository.findByRoleIdAndAuthorityId(roleId, authorityId)
                .orElseThrow(() -> new ResourceNotFoundException("Role authority not found."));
    }

    private Role getRole(Long roleId) {
        return roleRepository.findByIdAndDeletedAtIsNull(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found."));
    }

    private void validateRoleExist(Long id) {
        if (!roleRepository.existsByIdAndDeletedAtIsNull(id)) {
            throw new DuplicateResourceException("Role not found.");
        }
    }

    private Authority getAuthority(Long id) {
        return authorityRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Authority not found."));
    }

    private void validateAuthorityExist(Long id) {
        if (!authorityRepository.existsByIdAndDeletedAtIsNull(id))
            throw new DuplicateResourceException("Authority not found.");
    }
}
