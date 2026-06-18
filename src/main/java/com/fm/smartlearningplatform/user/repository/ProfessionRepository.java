package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.Authority;
import com.fm.smartlearningplatform.user.model.Profession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessionRepository extends JpaRepository<Profession, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Profession> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Profession> findByNameAndDeletedAtIsNull(String name);

    boolean existsByIdNotAndNameAndDeletedAtIsNull(Long id, String name);

    List<Profession> findByDeletedAtIsNullOrderByNameAsc();

    List<Profession> findByDeletedAtIsNullAndNameContainingIgnoreCase(String keyword);

    List<Profession> findByIdInAndDeletedAtIsNull(List<Long> ids);

    Page<Profession> findByDeletedAtIsNull(Pageable pageable);
    Page<Profession> findByDeletedAtIsNullAndNameContainingIgnoreCase(String keyword, Pageable pageable);

}