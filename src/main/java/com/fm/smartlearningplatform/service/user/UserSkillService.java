package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Skill;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserSkill;
import com.fm.smartlearningplatform.repository.user.SkillRepository;
import com.fm.smartlearningplatform.repository.user.UserRepository;
import com.fm.smartlearningplatform.repository.user.UserSkillRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserSkillService {

    public UserSkill addSkillToUser(Long userId, Long skillId);

    // ─── Find ────────────────────────────────────────────────

    public List<UserSkill> getUserSkills(Long userId) ;

    public List<UserSkill> getSkillUsers(Long skillId) ;

    public UserSkill findByUserIdAndSkillId(Long userId, Long skillId);

    // ─── Remove Skill from User ──────────────────────────────

    public void removeSkillFromUser(Long userId, Long skillId);

    public void deleteById(Long id);
}
