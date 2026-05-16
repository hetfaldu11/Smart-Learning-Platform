package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.Skill;
import com.fm.smartlearningplatform.model.User;
import com.fm.smartlearningplatform.repository.SkillRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

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