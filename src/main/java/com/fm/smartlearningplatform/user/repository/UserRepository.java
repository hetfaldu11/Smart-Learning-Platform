package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.Authority;
import com.fm.smartlearningplatform.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<User> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByEmailAndDeletedAtIsNull(String email);

    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    boolean existsByPhoneNumberAndDeletedAtIsNull(String phoneNumber);

    Optional<User> findByPhoneNumberAndDeletedAtIsNull(String phoneNumber);

    Page<User> findByDeletedAtIsNull(Pageable pageable);
    // ─── Find With Relationships ─────────────────────────────

    @EntityGraph(attributePaths = "userSkills")
    Optional<User> findWithUserSkillsByIdAndDeletedAtIsNull(Long id);

    @EntityGraph(attributePaths = "userInterests")
    Optional<User> findWithUserInterestsByIdAndDeletedAtIsNull(Long id);

    @EntityGraph(attributePaths = "userRoles")
    Optional<User> findWithUserRolesByIdAndDeletedAtIsNull(Long id);

    @EntityGraph(attributePaths = {
            "userSocialLinks",
            "userSocialLinks.platform"
    })
    Optional<User> findWithUserSocialLinksByIdAndDeletedAtIsNull(Long id);

    @EntityGraph(attributePaths = {
            "userProfile",
            "userPreference",
            "userVerification"
    })
    Optional<User> findWithUserDetailsByIdAndDeletedAtIsNull(Long id);

    // ─── Security ────────────────────────────────────────────

    @Query("""
                SELECT DISTINCT a
                FROM UserRole ur
                JOIN ur.role r
                JOIN RoleAuthority ra ON ra.role = r
                JOIN ra.authority a
                WHERE ur.user.id = :userId
                  AND ur.user.deletedAt IS NULL
                  AND r.deletedAt IS NULL
                  AND a.deletedAt IS NULL
            """)
    Set<Authority> findAuthoritiesByUserId(
            @Param("userId") Long userId
    );
}