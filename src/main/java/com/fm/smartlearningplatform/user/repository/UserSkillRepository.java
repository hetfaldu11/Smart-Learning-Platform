package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    // ─── Find ────────────────────────────────────────────────

    List<UserSkill> findByUserId(Long userId);

    List<UserSkill> findBySkillId(Long skillId);

    boolean existsByUserIdAndSkillId(Long userId, Long skillId);

    Optional<UserSkill> findByUserIdAndSkillId(Long userId, Long skillId);

    Optional<UserSkill> findByIdAndUserId(Long id, Long userId);

    @Query("""
            SELECT us.skill.id
            FROM UserSkill us
            WHERE us.user.id = :userId
            """)
    Set<Long> findSkillIdsByUserId(@Param("userId") Long userId);

    // ─── Delete ────────────────────────────────────────────────

    // Hard delete all user_skills for a skill (used when skill is deleted)
    @Modifying
//    @Query("DELETE FROM UserSkill us WHERE us.skill.id = :skillId")
    int deleteBySkillId(Long skillId);
}
