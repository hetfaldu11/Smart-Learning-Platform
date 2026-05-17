package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.Skill;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserSkillRepository extends JpaRepository<UserSkill,Long> {

    List<UserSkill> findByUserId(Long userId);

    List<UserSkill> findBySkillId(Long skillId);

    Optional<UserSkill> findByUserIdAndSkillId(Long userId, Long skillId);

    Optional<UserSkill> findByUserAndSkill(User user, Skill skill);

    boolean existsByUserIdAndSkillId(Long userId, Long skillId);

    // Hard delete all user_skills for a user (used when user is deleted)
    @Modifying
    @Query("DELETE FROM UserSkill us WHERE us.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    // Hard delete all user_skills for a skill (used when skill is deleted)
    @Modifying
    @Query("DELETE FROM UserSkill us WHERE us.skill.id = :skillId")
    void deleteBySkillId(@Param("skillId") Long skillId);
}
