package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Skill> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Skill> findByNameAndDeletedAtIsNull(String name);

    boolean existsByIdNotAndNameAndDeletedAtIsNull(Long id, String name);



    Page<Skill> findByDeletedAtIsNull(Pageable pageable);

    Page<Skill> findByDeletedAtIsNullAndNameContainingIgnoreCase(String keyword, Pageable pageable);
    List<Skill> findByIdInAndDeletedAtIsNull(List<Long> ids);
}