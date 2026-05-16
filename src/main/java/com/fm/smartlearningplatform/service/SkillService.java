package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.Skill;

public interface SkillService {
    void save(Skill skill);
    Skill findById(Long id);
    void deleteById(Long id);
}
