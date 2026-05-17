package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.*;

import java.util.List;

public interface UserRoleService {
    public UserRole addRoleToUser(Long userId, Long roleId);

    // ─── Find ────────────────────────────────────────────────

    public List<UserRole> getUserRoles(Long userId) ;

    public List<UserRole> getRoleUsers(Long roleId) ;

    public UserRole findByUserIdAndRoleId(Long userId, Long roleId);

    // ─── Remove Role from User ──────────────────────────────

    public void removeRoleFromUser(Long userId, Long roleId);

    public void deleteById(Long id);
}