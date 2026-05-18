package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id);

    Optional<Skill> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Skill> findByNameAndDeletedAtIsNull(String name);

    boolean existsByName(String name);

    Optional<Skill> findByName(String name);

    List<Skill> findByDeletedAtIsNull();
}
