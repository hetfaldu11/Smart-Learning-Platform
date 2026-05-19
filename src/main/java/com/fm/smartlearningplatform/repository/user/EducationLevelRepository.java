package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.EducationLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EducationLevelRepository extends JpaRepository<EducationLevel, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<EducationLevel> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<EducationLevel> findByNameAndDeletedAtIsNull(String name);

    boolean existsByName(String name);

    boolean existsByIdNotAndName(Long id, String name);

    Optional<EducationLevel> findByName(String name);

    List<EducationLevel> findByDeletedAtIsNull();
}
