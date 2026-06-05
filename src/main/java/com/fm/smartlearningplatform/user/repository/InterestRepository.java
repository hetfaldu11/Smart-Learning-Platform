package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.Authority;
import com.fm.smartlearningplatform.user.model.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Interest> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Interest> findByNameAndDeletedAtIsNull(String name);

    boolean existsByIdNotAndNameAndDeletedAtIsNull(Long id, String name);

    List<Interest> findByDeletedAtIsNullOrderByNameAsc();

    List<Interest> findByDeletedAtIsNullAndNameContainingIgnoreCase(String keyword);

    List<Interest> findByIdInAndDeletedAtIsNull(List<Long> ids);

    Page<Interest> findByDeletedAtIsNull(Pageable pageable);
    Page<Interest> findByDeletedAtIsNullAndNameContainingIgnoreCase(String keyword, Pageable pageable);



}