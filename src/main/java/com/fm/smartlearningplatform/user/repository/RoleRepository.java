package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Role> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Role> findByNameAndDeletedAtIsNull(String name);

    boolean existsByName(String name);

    boolean existsByIdNotAndName(Long id, String name);

    Optional<Role> findByName(String name);

    boolean existsByIdNotAndNameAndDeletedAtIsNull(Long id, String name);

    List<Role> findByDeletedAtIsNull();
}
