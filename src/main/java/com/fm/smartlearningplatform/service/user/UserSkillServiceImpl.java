package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Skill;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserSkill;
import com.fm.smartlearningplatform.repository.user.SkillRepository;
import com.fm.smartlearningplatform.repository.user.UserRepository;
import com.fm.smartlearningplatform.repository.user.UserSkillRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSkillServiceImpl implements UserSkillService{

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    @Autowired
    public UserSkillServiceImpl(UserSkillRepository userSkillRepository, UserRepository userRepository, SkillRepository skillRepository) {
        this.userSkillRepository = userSkillRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    // ─── Assign Skill to User ────────────────────────────────

    @Transactional
    public UserSkill addSkillToUser(Long userId, Long skillId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        Skill skill = skillRepository.findByIdAndDeletedAtIsNull(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found: " + skillId));

        // Prevent duplicate
        if (userSkillRepository.existsByUserIdAndSkillId(userId, skillId)) {
            throw new RuntimeException("User already has this skill");
        }

        UserSkill userSkill = UserSkill.builder()
                .user(user)
                .skill(skill)
                .build();

        return userSkillRepository.save(userSkill);
    }

    // ─── Find ────────────────────────────────────────────────

    public List<UserSkill> getUserSkills(Long userId) {
        return userSkillRepository.findByUserId(userId);
    }

    public List<UserSkill> getSkillUsers(Long skillId) {
        return userSkillRepository.findBySkillId(skillId);
    }

    public UserSkill findByUserIdAndSkillId(Long userId, Long skillId) {
        return userSkillRepository.findByUserIdAndSkillId(userId, skillId)
                .orElse(null);
    }

    // ─── Remove Skill from User ──────────────────────────────

    @Transactional
    public void removeSkillFromUser(Long userId, Long skillId) {
        if (!userSkillRepository.existsByUserIdAndSkillId(userId, skillId)) {
            throw new RuntimeException("User does not have this skill");
        }
        userSkillRepository.deleteByUserId(userId); // or specific row
    }

    @Transactional
    public void deleteById(Long id) {
        userSkillRepository.deleteById(id);
    }

}
