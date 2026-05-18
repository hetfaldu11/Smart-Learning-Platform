package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
//    List<Role> findByDeletedAtIsNull();
//
//    Optional<Role> findByIdAndDeletedAtIsNull(Long id);
//
//    boolean existsByNameAndDeletedAtIsNull(String name);
}
