package com.fm.smartlearningplatform.repository.user;


import com.fm.smartlearningplatform.model.user.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    
    // ─── Find ────────────────────────────────────────────────

    List<UserRole> findByUserId(Long userId);

    List<UserRole> findByRoleId(Long roleId);

    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

    Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);

    boolean existsByUserAndRole(User user, Role role);

    Optional<UserRole> findByUserAndRole(User user, Role role);


    // ─── Delete ────────────────────────────────────────────────

    // Hard delete all user_roles for a role (used when role is deleted)
    @Modifying
    @Transactional
    @Query("DELETE FROM UserRole us WHERE us.role.id = :roleId")
    void deleteByRoleId(@Param("roleId") Long roleId);
}
