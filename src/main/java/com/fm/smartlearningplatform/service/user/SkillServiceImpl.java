package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Skill;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.repository.user.SkillRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillServiceImpl implements SkillService{

    @Autowired
    SkillRepository skillRepository;

    @Override
    public void save(Skill skill) {
        skillRepository.save(skill);
    }

    @Override
    public Skill findById(Long id) {
        return skillRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id){
        Skill skill = skillRepository.findById(id).orElse(null);
        for(User user : skill.getUsers()){
            user.removeSkill(skill);
        }
        skillRepository.delete(skill);
    }
}