package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenderRepository extends JpaRepository<Gender, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Gender> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Gender> findByNameAndDeletedAtIsNull(String name);

    boolean existsByName(String name);

    Optional<Gender> findByName(String name);

    List<Gender> findByDeletedAtIsNull();
}
