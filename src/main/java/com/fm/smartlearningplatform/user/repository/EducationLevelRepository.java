package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.EducationLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EducationLevelRepository extends JpaRepository<EducationLevel, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<EducationLevel> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<EducationLevel> findByNameAndDeletedAtIsNull(String name);

    boolean existsByIdNotAndNameAndDeletedAtIsNull(Long id, String name);

    List<EducationLevel> findByDeletedAtIsNullOrderByNameAsc();

    List<EducationLevel> findByDeletedAtIsNullAndNameContainingIgnoreCase(String keyword);

    List<EducationLevel> findByIdInAndDeletedAtIsNull(List<Long> ids);
}