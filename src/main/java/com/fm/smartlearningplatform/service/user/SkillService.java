package com.fm.smartlearningplatform.service.user;
import com.fm.smartlearningplatform.dto.user.skill.request.CreateSkillRequest;
import com.fm.smartlearningplatform.dto.user.skill.request.UpdateSkillRequest;
import com.fm.smartlearningplatform.dto.user.skill.response.SkillResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.SkillMapper;
import com.fm.smartlearningplatform.model.user.Skill;
import com.fm.smartlearningplatform.repository.user.SkillRepository;
import com.fm.smartlearningplatform.repository.user.UserSkillRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final UserSkillRepository userSkillRepository;
    private final SkillMapper skillMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public SkillResponse createSkill(CreateSkillRequest request) {
        if (skillRepository.existsByNameAndDeletedAtIsNull(request.getName()))
            throw new DuplicateResourceException("Skill already exists.");

        return skillMapper.toResponse(
                skillRepository.save(
                        skillMapper.toEntity(request)
                )
        );
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public SkillResponse updateSkill(Long id, UpdateSkillRequest request) {
        Skill skill = skillRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found."));

        if (skillRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.getName()))
            throw new DuplicateResourceException("Skill already exists.");

        skillMapper.updateSkillFromRequest(request, skill);

        return skillMapper.toResponse(skillRepository.save(skill));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean existsByIdAndDeletedAtIsNull(Long id) {
        return skillRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public SkillResponse findByIdAndDeletedAtIsNull(Long id){
         return skillMapper.toResponse(
                 skillRepository.findByIdAndDeletedAtIsNull(id)
                         .orElseThrow(() -> new ResourceNotFoundException("Skill not exists.")));
    }

    @Transactional(readOnly = true)
     public boolean existsByNameAndDeletedAtIsNull(String name){
        return skillRepository.existsByNameAndDeletedAtIsNull(name);
    }

    @Transactional(readOnly = true)
    public SkillResponse findByNameAndDeletedAtIsNull(String name){
        return skillMapper.toResponse(
                skillRepository.findByNameAndDeletedAtIsNull(name)
                        .orElseThrow(()->new ResourceNotFoundException("Skill not exists."))
        );
    }

    @Transactional(readOnly = true)
    public List<SkillResponse> findAllActive() {
        return skillRepository.findByDeletedAtIsNull()
                .stream()
                .map(skillMapper::toResponse)
                .toList();
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id){
        Skill skill = skillRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not exist."));

        userSkillRepository.deleteBySkillId(id);

        skill.setDeletedAt(LocalDateTime.now());

        skillRepository.save(skill);
    }
}
