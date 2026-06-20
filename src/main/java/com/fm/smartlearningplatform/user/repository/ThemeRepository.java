package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Theme> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Theme> findByNameAndDeletedAtIsNull(String name);

    boolean existsByIdNotAndNameAndDeletedAtIsNull(Long id, String name);

    List<Theme> findByDeletedAtIsNullOrderByNameAsc();

    List<Theme> findByDeletedAtIsNullAndNameContainingIgnoreCase(String keyword);

    List<Theme> findByIdInAndDeletedAtIsNull(List<Long> ids);

    Page<Theme> findByDeletedAtIsNull(Pageable pageable);

    Page<Theme> findByDeletedAtIsNullAndNameContainingIgnoreCase(String keyword, Pageable pageable);

}