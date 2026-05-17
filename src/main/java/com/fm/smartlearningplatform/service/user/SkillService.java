package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Skill;

public interface SkillService {
    void save(Skill skill);
    Skill findById(Long id);
    void deleteById(Long id);
}
