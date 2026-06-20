package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.skill.request.CreateSkillRequest;
import com.fm.smartlearningplatform.user.dto.skill.request.UpdateSkillRequest;
import com.fm.smartlearningplatform.user.dto.skill.response.DeleteSkillResponse;
import com.fm.smartlearningplatform.user.dto.skill.response.SkillResponse;
import com.fm.smartlearningplatform.user.mapper.SkillMapper;
import com.fm.smartlearningplatform.user.model.Skill;
import com.fm.smartlearningplatform.user.repository.SkillRepository;
import com.fm.smartlearningplatform.user.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final UserSkillRepository userSkillRepository;
    private final SkillMapper skillMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public SkillResponse create(CreateSkillRequest request) {
        validateSkillNotExist(request.name());
        return skillMapper.toResponse(skillRepository.save(skillMapper.toEntity(request)));
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public SkillResponse update(Long id, UpdateSkillRequest request) {

        Skill skill = getSkill(id);

        if (skillRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("Skill already exist.");

        skillMapper.updateSkillFromRequest(request, skill);
        return skillMapper.toResponse(skillRepository.save(skill));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean foundById(Long id) {
        return skillRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public SkillResponse findById(Long id) {
        return skillMapper.toResponse(getSkill(id));
    }

    @Transactional(readOnly = true)
    public Page<SkillResponse> searchByKeyword(String keyword, Pageable pageable) {
        Page<Skill> skills;
        if (keyword == null || keyword.isBlank()) {
            skills = skillRepository.findByDeletedAtIsNull(pageable);
        } else {
            keyword = keyword.trim();
            skills = skillRepository.findByDeletedAtIsNullAndNameContainingIgnoreCase(keyword, pageable);
        }
        return skills.map(skillMapper::toResponse);
    }

    // ─── Delete ────────────────────────────────────────────────

    @Transactional
    public DeleteSkillResponse deleteById(Long id) {
        Skill skill = getSkill(id);
        userSkillRepository.deleteBySkillId(id);

        skill.setDeletedAt(LocalDateTime.now());

        skillRepository.save(skill);

        return new DeleteSkillResponse("Skill is deleted successfully.");
    }

    // ─── Helper ────────────────────────────────────────────────

    private void validateSkillNotExist(String name) {
        if (skillRepository.existsByNameAndDeletedAtIsNull(name))
            throw new DuplicateResourceException("Skill already exist.");
    }

    private Skill getSkill(Long id) {
        return skillRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found."));
    }
}
