package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.model.Role;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserRole;
import com.fm.smartlearningplatform.user.repository.RoleRepository;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import com.fm.smartlearningplatform.user.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public UserRole createUserRole(User user, Role role) {
        if (!userRepository.existsByIdAndDeletedAtIsNull(user.getId()))
            throw new ResourceNotFoundException("User not found.");

        if (!roleRepository.existsByIdAndDeletedAtIsNull(role.getId()))
            throw new ResourceNotFoundException("Role not found.");

        if (userRoleRepository.existsByUserAndRole(user, role))
            throw new DuplicateResourceException("UserRole already exists.");

        return userRoleRepository.save(UserRole.builder()
                .user(user)
                .role(role)
                .build());
    }

    @Transactional
    public UserRole addRole(Long userId, Long roleId) {

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Role role = roleRepository.findByIdAndDeletedAtIsNull(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found."));

        if (userRoleRepository.existsByUserAndRole(user, role))
            throw new DuplicateResourceException("UserRole already exists.");

        return userRoleRepository.save(UserRole.builder()
                .user(user)
                .role(role)
                .build());
    }

    // ─── Find ────────────────────────────────────────────────

    public List<UserRole> findByUserId(Long id) {
        if (!userRepository.existsByIdAndDeletedAtIsNull(id))
            throw new ResourceNotFoundException("User not found.");

        return userRoleRepository.findByUserId(id);
    }

    public List<UserRole> findByRoleId(Long id) {
        if (!roleRepository.existsByIdAndDeletedAtIsNull(id))
            throw new ResourceNotFoundException("Role not found.");

        return userRoleRepository.findByRoleId(id);
    }

    public boolean existByUserIdAndRoleId(Long userId, Long roleId) {
        return userRoleRepository.existsByUserIdAndRoleId(userId, roleId);
    }

    public UserRole findByUserIdAndRoleId(Long userId, Long roleId) {
        if (!userRepository.existsByIdAndDeletedAtIsNull(userId)) {
            throw new ResourceNotFoundException("User not found.");
        }

        if (!roleRepository.existsByIdAndDeletedAtIsNull(roleId)) {
            throw new ResourceNotFoundException("Role not found.");
        }

        return userRoleRepository.findByUserIdAndRoleId(userId, roleId)
                .orElseThrow(() -> new ResourceNotFoundException("UserRole not found."));
    }

    public boolean existByUserAndRole(User user, Role role) {
        return userRoleRepository.existsByUserAndRole(user, role);
    }

    public UserRole findByUserAndRole(User user, Role role) {
        if (!userRepository.existsByIdAndDeletedAtIsNull(user.getId())) {
            throw new ResourceNotFoundException("User not found.");
        }

        if (!roleRepository.existsByIdAndDeletedAtIsNull(role.getId())) {
            throw new ResourceNotFoundException("Role not found.");
        }
        return userRoleRepository.findByUserAndRole(user, role)
                .orElseThrow(() -> new ResourceNotFoundException("UserRole not found."));
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id) {
        if (!userRoleRepository.existsById(id))
            throw new ResourceNotFoundException("UserRole not found.");

        userRoleRepository.deleteById(id);
    }
}
