package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.*;
import com.fm.smartlearningplatform.repository.user.RoleRepository;
import com.fm.smartlearningplatform.repository.user.UserRepository;
import com.fm.smartlearningplatform.repository.user.UserRoleRepository;
import com.fm.smartlearningplatform.repository.user.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository RoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository, RoleRepository RoleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.RoleRepository = RoleRepository;
    }

    // ─── Assign Role to User ────────────────────────────────

    @Transactional
    public UserRole addRoleToUser(Long userId, Long roleId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        Role role = RoleRepository.findByIdAndDeletedAtIsNull(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));

        // Prevent duplicate
        if (userRoleRepository.existsByUserIdAndRoleId(userId, roleId)) {
            throw new RuntimeException("User already has this Role");
        }

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(role)
                .build();

        return userRoleRepository.save(userRole);
    }

    // ─── Find ────────────────────────────────────────────────

    public List<UserRole> getUserRoles(Long userId) {
        return userRoleRepository.findByUserId(userId);
    }

    public List<UserRole> getRoleUsers(Long roleId) {
        return userRoleRepository.findByRoleId(roleId);
    }

    public UserRole findByUserIdAndRoleId(Long userId, Long roleId) {
        return userRoleRepository.findByUserIdAndRoleId(userId, roleId)
                .orElse(null);
    }

    // ─── Remove Role from User ──────────────────────────────

    @Transactional
    public void removeRoleFromUser(Long userId, Long roleId) {
        if (!userRoleRepository.existsByUserIdAndRoleId(userId, roleId)) {
            throw new RuntimeException("User does not have this Role");
        }
        userRoleRepository.deleteByUserId(userId); // or specific row
    }

    @Transactional
    public void deleteById(Long id) {
        userRoleRepository.deleteById(id);
    }
}