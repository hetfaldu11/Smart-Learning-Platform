package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenderRepository extends JpaRepository<Gender, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Gender> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Gender> findByNameAndDeletedAtIsNull(String name);

    boolean existsByIdNotAndNameAndDeletedAtIsNull(Long id, String name);

    List<Gender> findByDeletedAtIsNullOrderByNameAsc();

    List<Gender> findByDeletedAtIsNullAndNameContainingIgnoreCase(String keyword);

    List<Gender> findByIdInAndDeletedAtIsNull(List<Long> ids);

    Page<Gender> findByDeletedAtIsNull(Pageable pageable);

    Page<Gender> findByDeletedAtIsNullAndNameContainingIgnoreCase(String keyword, Pageable pageable);
}