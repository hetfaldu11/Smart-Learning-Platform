package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.dto.user.userSkill.request.CreateUserSkillRequest;
import com.fm.smartlearningplatform.dto.user.userSkill.response.UserSkillResponse;
import com.fm.smartlearningplatform.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.mapper.user.UserSkillMapper;
import com.fm.smartlearningplatform.model.user.Skill;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserSkill;
import com.fm.smartlearningplatform.repository.user.SkillRepository;
import com.fm.smartlearningplatform.repository.user.UserRepository;
import com.fm.smartlearningplatform.repository.user.UserSkillRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final UserSkillMapper userSkillMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public UserSkillResponse createUserSkill(CreateUserSkillRequest request) {

        if (userSkillRepository.existsByUserIdAndSkillId(request.getUserId(), request.getSkillId()))
            throw new DuplicateResourceException("UserSkill already found.");

        User user = userRepository.findByIdAndDeletedAtIsNull(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Skill skill = skillRepository.findByIdAndDeletedAtIsNull(request.getSkillId())
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found."));

        UserSkill userSkill = user.addSkill(skill);
        userRepository.save(user);

        return userSkillMapper.toResponse(userSkill);
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<UserSkillResponse> findByUserId(Long id){
        if(!userRepository.existsByIdAndDeletedAtIsNull(id))
            throw new ResourceNotFoundException("User not found.");

        return userSkillRepository.findByUserId(id)
                .stream()
                .map(userSkillMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserSkillResponse> findBySkillId(Long id){
        if(!skillRepository.existsByIdAndDeletedAtIsNull(id))
            throw new ResourceNotFoundException("Skill not found.");

        return userSkillRepository.findBySkillId(id)
                .stream()
                .map(userSkillMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserSkillResponse findByUserIdAndSkillId(Long userId,Long skillId){
        if(!userRepository.existsByIdAndDeletedAtIsNull(userId)) {
            throw new ResourceNotFoundException("User not found.");
        }

        if(!skillRepository.existsByIdAndDeletedAtIsNull(skillId)) {
            throw new ResourceNotFoundException("Skill not found.");
        }

        return userSkillMapper.toResponse(userSkillRepository.findByUserIdAndSkillId(userId,skillId).
                orElseThrow(() -> new ResourceNotFoundException("UserSkill not found.")));
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id){
        UserSkill userSkill = userSkillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserSkill not found."));
        userSkillRepository.delete(userSkill);
    }
}
