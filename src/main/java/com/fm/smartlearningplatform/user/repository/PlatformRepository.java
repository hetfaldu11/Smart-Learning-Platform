package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.Authority;
import com.fm.smartlearningplatform.user.model.Platform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlatformRepository extends JpaRepository<Platform, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Platform> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Platform> findByNameAndDeletedAtIsNull(String name);

    boolean existsByIdNotAndNameAndDeletedAtIsNull(Long id, String name);

    List<Platform> findByDeletedAtIsNullOrderByNameAsc();

    List<Platform> findByDeletedAtIsNullAndNameContainingIgnoreCase(String keyword);

    List<Platform> findByIdInAndDeletedAtIsNull(List<Long> ids);

    Page<Platform> findByDeletedAtIsNull(Pageable pageable);
    Page<Platform> findByDeletedAtIsNullAndNameContainingIgnoreCase(String keyword, Pageable pageable);

}