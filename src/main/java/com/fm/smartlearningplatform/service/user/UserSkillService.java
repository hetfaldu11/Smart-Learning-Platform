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
public class UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    @Autowired
    public UserSkillService(UserSkillRepository userSkillRepository, UserRepository userRepository, SkillRepository skillRepository) {
        this.userSkillRepository = userSkillRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    UserSkill createUserSkill(User user, Skill skill){

        if(userRepository.existsById(user.getId()) == false)
            throw new RuntimeException("User is not existed.");

        if(skillRepository.existsById(skill.getId()) == false)
            throw new RuntimeException("Skill is not existed.");

        if(userSkillRepository.existsByUserAndSkill(user,skill))
            throw new RuntimeException("UserSkill is already existed.");

        UserSkill userSkill= user.addSkill(skill);

        userRepository.save(user);

        return userSkill;
    }

    @Transactional
    public UserSkill addSkill(Long userId, Long skillId){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User is not existed."));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill is not existed."));

        if(userSkillRepository.existsByUserAndSkill(user,skill))
            throw new RuntimeException("UserSkill is already existed.");

        UserSkill userSkill = user.addSkill(skill);

        userRepository.save(user);

        return userSkill;
    }

    // ─── Find ────────────────────────────────────────────────

    public List<UserSkill> findByUserId(Long id){
        if(userRepository.existsByIdAndDeletedAtIsNull(id) == false)
            throw new RuntimeException("User is not exists.");

        return userSkillRepository.findByUserId(id);
    }

    public List<UserSkill> findBySkillId(Long id){
        if(skillRepository.existsByIdAndDeletedAtIsNull(id) == false)
            throw new RuntimeException("Skill is not exists.");

        return userSkillRepository.findByUserId(id);
    }

    public boolean existByUserIdAndSkillId(Long userId,Long skillId){
        return userSkillRepository.existsByUserIdAndSkillId(userId,skillId);
    }

    public UserSkill findByUserIdAndSkillId(Long userId,Long skillId){
        if(userRepository.existsByIdAndDeletedAtIsNull(userId) == false) {
            throw new RuntimeException("User is not exists.");
        }

        if(skillRepository.existsByIdAndDeletedAtIsNull(skillId) == false) {
            throw new RuntimeException("skill  is Not exists.");
        }

        return userSkillRepository.findByUserIdAndSkillId(userId,skillId).orElseThrow(() -> new RuntimeException("UserSkill is not existed."));
    }

    public boolean existByUserAndSkill(User user,Skill skill){
        return userSkillRepository.existsByUserAndSkill(user,skill);
    }

    public UserSkill findByUserAndSkill(User user,Skill skill){
        if(userRepository.existsByIdAndDeletedAtIsNull(user.getId()) == false) {
            throw new RuntimeException("User is not exists.");
        }

        if(skillRepository.existsByIdAndDeletedAtIsNull(skill.getId()) == false) {
            throw new RuntimeException("Skill is not exists.");
        }
        return userSkillRepository.findByUserAndSkill(user,skill).orElseThrow(() -> new RuntimeException("UserSkill is not existed."));
    }

    // ─── Delete ────────────────────────────────────────────────

    public void deleteById(Long id){
        if(userSkillRepository.existsById(id) == false)
            throw new RuntimeException("UserSkill is not existed.");

        userSkillRepository.deleteById(id);
    }
}
