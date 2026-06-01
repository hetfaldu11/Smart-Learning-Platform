package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Authority> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Authority> findByNameAndDeletedAtIsNull(String name);

    boolean existsByName(String name);

    boolean existsByIdNotAndName(Long id, String name);

    Optional<Authority> findByName(String name);

    boolean existsByIdNotAndNameAndDeletedAtIsNull(Long id, String name);

    List<Authority> findByDeletedAtIsNull();
}
