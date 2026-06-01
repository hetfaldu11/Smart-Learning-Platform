package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.Authority;
import com.fm.smartlearningplatform.user.model.Role;
import com.fm.smartlearningplatform.user.model.RoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleAuthorityRepository extends JpaRepository<RoleAuthority, Long> {

    // ─── Find ────────────────────────────────────────────────

    List<RoleAuthority> findByRoleId(Long roleId);

    List<RoleAuthority> findByAuthorityId(Long roleId);

    boolean existsByIdAndAuthorityId(Long roleId, Long authorityId);

    Optional<RoleAuthority> findByRoleIdAndAuthorityId(Long roleId, Long authorityId);

    boolean existsByRoleAndAuthority(Role role, Authority authority);

    Optional<RoleAuthority> findByRoleAndAuthority(Role role, Authority authority);


    // ─── Delete ────────────────────────────────────────────────

    // Hard delete all role_roles for a role (used when role is deleted)
    @Modifying
    @Query("DELETE FROM RoleAuthority us WHERE us.role.id = :roleId")
    void deleteByAuthorityId(@Param("roleId") Long roleId);

    @Query("""
            SELECT ra.authority
            FROM RoleAuthority ra
            WHERE ra.role.id = :roleId
            """)
    List<Authority> findAuthoritiesByRoleId(@Param("roleId") Long roleId);

}