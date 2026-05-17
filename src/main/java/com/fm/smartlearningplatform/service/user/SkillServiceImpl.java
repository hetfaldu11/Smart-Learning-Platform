package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Skill;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.repository.user.SkillRepository;
import com.fm.smartlearningplatform.repository.user.UserSkillRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SkillServiceImpl implements SkillService{

    @Autowired
    SkillRepository skillRepository;
    @Autowired
    private UserSkillRepository userSkillRepository;

    //  ─────Create───────────────────────────────────────────

    @Override
    public Skill createSkill(String name) {
        Skill skill = Skill.builder()
                .name(name)
                .build();

        skillRepository.save(skill);
        return skill;
    }

    //  ─────Find─────────────────────────────────────────────

    @Override
    public Skill findById(Long id) {
        return skillRepository.findByIdAndDeletedAtIsNull(id).orElse(null);
    }

    @Override
    public List<Skill> findAll() {
        return skillRepository.findByDeletedAtIsNull();
    }

    //  ─────Delete─────────────────────────────────────────────

    @Override
    public void deleteById(Long id){
        Skill skill = skillRepository.findByIdAndDeletedAtIsNull(id).orElse(null);
        userSkillRepository.deleteBySkillId(id);
        if(skill == null)
            return;
        skill.setDeletedAt(LocalDateTime.now());
        skillRepository.save(skill);
    }
}