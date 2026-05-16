package com.fm.smartlearningplatform.repository;


import com.fm.smartlearningplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("""
            SELECT u FROM User u
            LEFT JOIN FETCH u.skills
            WHERE u.id = :id
            """)
    Optional<User> findUserWithSkills(Long id);

    @Query("""
            SELECT u FROM User u
            LEFT JOIN FETCH u.interests
            WHERE u.id = :id
            """)
    Optional<User> findUserWithInterests(Long id);

    @Query("""
            SELECT u FROM User u
            LEFT JOIN FETCH u.authorizations
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
            LEFT JOIN FETCH u.skills
            LEFT JOIN FETCH u.interests
            LEFT JOIN FETCH u.userSocialLinks
            LEFT JOIN FETCH u.authorizations
            WHERE u.id = :id
            """)
    Optional<User> findFullUser(Long id);


}
