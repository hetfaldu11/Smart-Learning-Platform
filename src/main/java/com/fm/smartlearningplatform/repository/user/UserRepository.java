package com.fm.smartlearningplatform.repository.user;


import com.fm.smartlearningplatform.model.user.Authority;
import com.fm.smartlearningplatform.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User,Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<User> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByEmailAndDeletedAtIsNull(String email);

    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    List<User> findByDeletedAtIsNull();

    boolean existsByPhoneNumberAndDeletedAtIsNull(String phoneNumber);

    Optional<User> findByPhoneNumberAndDeletedAtIsNull(String phoneNumber);

    // ─── Find Lazy────────────────────────────────────────────────

    @Query("""
            SELECT u FROM User u
            LEFT JOIN FETCH u.userSkills
            WHERE u.id = :id
            """)
    Optional<User> findUserWithSkills(Long id);

    @Query("""
            SELECT u FROM User u
            LEFT JOIN FETCH u.userInterests
            WHERE u.id = :id
            """)
    Optional<User> findUserWithInterests(Long id);

    @Query("""
            SELECT u FROM User u
            LEFT JOIN FETCH u.userRoles
            WHERE u.id = :id
            """)
    Optional<User> findUserWithUserRoles(Long id);

    @Query("""
            SELECT u FROM User u
            LEFT JOIN FETCH u.userSocialLinks
            WHERE u.id = :id
            """)
    Optional<User> findUserWithUserSocialLinks(Long id);

    @Query("""
            SELECT u FROM User u
            LEFT JOIN FETCH u.userSkills
            LEFT JOIN FETCH u.userInterests
            LEFT JOIN FETCH u.userSocialLinks
            LEFT JOIN FETCH u.userRoles
            WHERE u.id = :id
            """)
    Optional<User> findFullUser(Long id);

    @Query("""
        SELECT DISTINCT a
        FROM UserRole ur
        JOIN ur.role r
        JOIN RoleAuthority ra ON ra.role = r
        JOIN ra.authority a
        WHERE ur.user.id = :userId
    """)
    Set<Authority> findAuthoritiesByUserId(Long userId);
}