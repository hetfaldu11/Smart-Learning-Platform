package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Skill;
import com.fm.smartlearningplatform.repository.user.SkillRepository;
import com.fm.smartlearningplatform.repository.user.UserSkillRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final UserSkillRepository userSkillRepository;

    @Autowired
    public SkillService (SkillRepository skillRepository, UserSkillRepository userSkillRepository) {
        this.skillRepository = skillRepository;
        this.userSkillRepository = userSkillRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    Skill createSkill(String name){

        if(name==null) {
            throw new RuntimeException("Name is null.");
        }

        if(skillRepository.existsByName(name))
            throw new RuntimeException("Skill is already exist.");

        Skill skill = Skill.builder()
                .name(name)
                .build();

        return skillRepository.save(skill);
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public Skill updateSkill(Long id, String newName) {

        if(newName==null) {
            throw new RuntimeException("Name is null.");
        }

        Skill skill = skillRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Skill is not exist."));

        skill.setName(newName);

        return skillRepository.save(skill);
    }

    // ─── Find ────────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(Long id) {
        return skillRepository.existsByIdAndDeletedAtIsNull(id);
    }

    public Skill findByIdAndDeletedAtIsNull(Long id){
         Skill skill = skillRepository.findById(id)
                         .orElseThrow(()->new RuntimeException("Skill is not existed."));

         if(skill.getDeletedAt() != null)
             throw new RuntimeException("Skill is deleted.");
         return skill;
    }

    boolean existsByNameAndDeletedAtIsNull(String name){
        return skillRepository.existsByNameAndDeletedAtIsNull(name);
    }

    Skill findByNameAndDeletedAtIsNull(String name){
        Skill skill = skillRepository.findByName(name)

                .orElseThrow(()->new RuntimeException("Skill is not existed."));

        if(skill.getDeletedAt() != null)
            throw new RuntimeException("Skill is deleted.");
        return skill;
    }

    List<Skill> findByDeletedAtIsNull(){
        return skillRepository.findByDeletedAtIsNull();
    }

    // ─── Delete ────────────────────────────────────────────────

    void deleteById(Long id){
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill is not exist."));

        if(skill.getDeletedAt() != null){
            throw  new RuntimeException("Skill is already deleted.");
        }

        userSkillRepository.deleteBySkillId(id);

        skill.setDeletedAt(LocalDateTime.now());

        skillRepository.save(skill);
    }
}
