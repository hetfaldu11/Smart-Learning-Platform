package com.fm.smartlearningplatform.service.user;
import com.fm.smartlearningplatform.dto.user.skill.request.CreateSkillRequest;
import com.fm.smartlearningplatform.dto.user.skill.request.UpdateSkillRequest;
import com.fm.smartlearningplatform.dto.user.skill.response.DeleteSkillResponse;
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
    public SkillResponse create(CreateSkillRequest request) {
        if (skillRepository.existsByNameAndDeletedAtIsNull(request.name()))
            throw new DuplicateResourceException("Skill already exist.");

        return skillMapper.toResponse(skillRepository.save(skillMapper.toEntity(request)));
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public SkillResponse update(Long id, UpdateSkillRequest request) {
        Skill skill = skillRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found."));

        if (skillRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("Skill already exist.");

        skillMapper.updateSkillFromRequest(request, skill);
        return skillMapper.toResponse(skillRepository.save(skill));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean foundById(Long id)
    {
        return skillRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public SkillResponse findById(Long id){
         return skillMapper.toResponse(
                 skillRepository.findByIdAndDeletedAtIsNull(id)
                         .orElseThrow(() -> new ResourceNotFoundException("Skill not found.")));
    }

    @Transactional(readOnly = true)
    public List<SkillResponse> searchByKeyword(String keyword){
        if(keyword == null || keyword.isBlank()){
            return findAll();
        }

        keyword = keyword.trim();
        return skillRepository.findByDeletedAtIsNullAndNameContainingIgnoreCase(keyword)
                .stream()
                .map(skillMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SkillResponse> findAll() {
        return skillRepository.findByDeletedAtIsNullOrderByNameAsc()
                .stream()
                .map(skillMapper::toResponse)
                .toList();
    }

    // ─── Delete ────────────────────────────────────────────────

    @Transactional
    public DeleteSkillResponse deleteById(Long id){
        Skill skill = skillRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found."));

        userSkillRepository.deleteBySkillId(id);

        skill.setDeletedAt(LocalDateTime.now());

        skillRepository.save(skill);

        return new DeleteSkillResponse("Skill is deleted successfully.");
    }
}