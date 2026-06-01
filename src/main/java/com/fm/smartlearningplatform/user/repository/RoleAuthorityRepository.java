package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.RoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleAuthorityRepository extends JpaRepository<RoleAuthority, Long> {

    // ─── Find ────────────────────────────────────────────────

    List<RoleAuthority> findByRoleId(Long roleId);

    List<RoleAuthority> findByAuthorityId(Long authorityId);

    boolean existsByRoleIdAndAuthorityId(Long roleId, Long authorityId);

    Optional<RoleAuthority> findByRoleIdAndAuthorityId(Long roleId, Long authorityId);

    Optional<RoleAuthority> findByIdAndRoleId(Long id, Long roleId);

    @Query("""
            SELECT us.authority.id
            FROM RoleAuthority us
            WHERE us.role.id = :roleId
            """)
    Set<Long> findAuthorityIdsByRoleId(@Param("roleId") Long roleId);

    // ─── Delete ────────────────────────────────────────────────

    // Hard delete all role_authority for a authority (used when authority is deleted)
    @Modifying
    @Query("DELETE FROM RoleAuthority us WHERE us.authority.id = :authorityId")
    void deleteByAuthorityId(Long authorityId);
}
