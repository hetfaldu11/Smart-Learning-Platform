package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
//    List<Skill> findByDeletedAtIsNull();
//
//    Optional<Skill> findByIdAndDeletedAtIsNull(Long id);
//
//    boolean existsByNameAndDeletedAtIsNull(String name);
}
