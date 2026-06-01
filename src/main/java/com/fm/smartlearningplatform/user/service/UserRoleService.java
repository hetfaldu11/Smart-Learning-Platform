package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.userRole.request.CreateUserRoleRequest;
import com.fm.smartlearningplatform.user.dto.userRole.request.CreateUserRolesRequest;
import com.fm.smartlearningplatform.user.dto.userRole.response.DeleteUserRoleResponse;
import com.fm.smartlearningplatform.user.dto.userRole.response.UserRoleResponse;
import com.fm.smartlearningplatform.user.mapper.UserRoleMapper;
import com.fm.smartlearningplatform.user.model.Role;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserRole;
import com.fm.smartlearningplatform.user.repository.RoleRepository;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import com.fm.smartlearningplatform.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleMapper userRoleMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public UserRoleResponse create(Long userId, CreateUserRoleRequest request) {
        User user = getUser(userId);

        Role role = getRole(request.roleId());

        validateUserRoleNotExist(userId, request.roleId());

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(role)
                .build();

        return userRoleMapper.toResponse(userRoleRepository.save(userRole));
    }

    @Transactional
    public List<UserRoleResponse> create(Long userId, CreateUserRolesRequest request) {

        Set<Long> uniqueRoleIds = new HashSet<>(request.roleIds());

        if (uniqueRoleIds.size() != request.roleIds().size()) {
            throw new DuplicateResourceException("Duplicate role ids are not allowed.");
        }

        User user = getUser(userId);

        List<Role> roles = roleRepository.findByIdInAndDeletedAtIsNull(new ArrayList<>(uniqueRoleIds));

        if (roles.size() != uniqueRoleIds.size()) {
            throw new ResourceNotFoundException("Some role ids do not exist.");
        }

        Set<Long> existingRoleIds = userRoleRepository.findRoleIdsByUserId(userId);

        List<UserRole> userRoles = new ArrayList<>(roles.size());

        for (Role role : roles) {
            if (existingRoleIds.contains(role.getId())) {
                throw new DuplicateResourceException(
                        "UserRole already exists."
                );
            }

            UserRole userRole = UserRole.builder()
                    .user(user)
                    .role(role)
                    .build();

            userRoles.add(userRole);
        }

        return userRoleRepository.saveAll(userRoles)
                .stream()
                .map(userRoleMapper::toResponse)
                .toList();
    }


    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<UserRoleResponse> findByUserId(Long userId) {
        validateUserExist(userId);

        return userRoleRepository.findByUserId(userId)
                .stream()
                .map(userRoleMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserRoleResponse> findByRoleId(Long roleId) {
        validateRoleExist(roleId);

        return userRoleRepository.findByRoleId(roleId)
                .stream()
                .map(userRoleMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserRoleResponse findByUserIdAndRoleId(Long userId, Long roleId) {
        validateUserExist(userId);

        validateRoleExist(roleId);

        return userRoleMapper.toResponse(getUserRole(userId, roleId));
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public DeleteUserRoleResponse deleteById(Long userId, Long roleId) {
        validateUserRoleExist(userId,roleId);
        UserRole userRole = getUserRole(userId, roleId);
        userRoleRepository.delete(userRole);
        return new DeleteUserRoleResponse("User role association deleted successfully.");
    }
    // ─── Helper ────────────────────────────────────────────────

    private void validateUserRoleNotExist(Long userId, Long roleId) {
        if (userRoleRepository.existsByUserIdAndRoleId(userId, roleId))
            throw new DuplicateResourceException("UserRole already exists.");
    }

    private void validateUserRoleExist(Long userId, Long roleId) {
        if (!userRoleRepository.existsByUserIdAndRoleId(userId, roleId))
            throw new ResourceNotFoundException("UserRole not found.");
    }

    private UserRole getUserRole(Long userId, Long roleId) {
        return userRoleRepository.findByUserIdAndRoleId(userId, roleId)
                .orElseThrow(() -> new ResourceNotFoundException("User role not found."));
    }

    private User getUser(Long userId) {
        return userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    private void validateUserExist(Long id) {
        if (!userRepository.existsByIdAndDeletedAtIsNull(id)) {
            throw new DuplicateResourceException("User not found.");
        }
    }

    private Role getRole(Long id) {
        return roleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found."));
    }

    private void validateRoleExist(Long id) {
        if (!roleRepository.existsByIdAndDeletedAtIsNull(id))
            throw new DuplicateResourceException("Role not found.");
    }
}
