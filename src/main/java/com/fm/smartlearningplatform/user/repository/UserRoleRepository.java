package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    // ─── Find ────────────────────────────────────────────────

    List<UserRole> findByUserId(Long userId);

    List<UserRole> findByRoleId(Long roleId);

    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

    Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);

    Optional<UserRole> findByIdAndUserId(Long id, Long userId);

    @Query("""
            SELECT us.role.id
            FROM UserRole us
            WHERE us.user.id = :userId
            """)
    Set<Long> findRoleIdsByUserId(@Param("userId") Long userId);

    // ─── Delete ────────────────────────────────────────────────

    // Hard delete all user_roles for a role (used when role is deleted)
    @Modifying
    @Query("DELETE FROM UserRole us WHERE us.role.id = :roleId")
    void deleteByRoleId(Long roleId);
}
