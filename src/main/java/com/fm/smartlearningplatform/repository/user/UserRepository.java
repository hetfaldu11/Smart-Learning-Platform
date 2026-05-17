package com.fm.smartlearningplatform.repository.user;


import com.fm.smartlearningplatform.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
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
    Optional<User> findUserWithUserAuthorizations(Long id);

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

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByIdAndDeletedAtIsNull(Long id);
}
