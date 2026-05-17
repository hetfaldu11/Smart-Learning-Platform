package com.fm.smartlearningplatform.repository.user;


import com.fm.smartlearningplatform.model.user.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByUserId(Long userId);

    List<UserRole> findByRoleId(Long roleId);

    Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);

    Optional<UserRole> findByUserAndRole(User user, Role role);

    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

    // Hard delete all user_Roles for a user (used when the user is deleted)
    @Modifying
    @Query("DELETE FROM UserRole us WHERE us.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    // Hard delete all user_Roles for a Role (used when Role is deleted)
    @Modifying
    @Query("DELETE FROM UserRole us WHERE us.role.id = :roleId")
    void deleteByRoleId(@Param("RoleId") Long roleId);
}
