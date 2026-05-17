package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Skill;

import java.util.List;

public interface SkillService {
    Skill findById(Long id);
    void deleteById(Long id);
    Skill createSkill(String name);
    List<Skill> findAll();
}
