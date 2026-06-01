package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.userSkill.request.CreateUserSkillRequest;
import com.fm.smartlearningplatform.user.dto.userSkill.request.CreateUserSkillsRequest;
import com.fm.smartlearningplatform.user.dto.userSkill.response.DeleteUserSkillResponse;
import com.fm.smartlearningplatform.user.dto.userSkill.response.UserSkillResponse;
import com.fm.smartlearningplatform.user.mapper.UserSkillMapper;
import com.fm.smartlearningplatform.user.model.Skill;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserSkill;
import com.fm.smartlearningplatform.user.repository.SkillRepository;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import com.fm.smartlearningplatform.user.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final UserSkillMapper userSkillMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public UserSkillResponse create(Long userId, CreateUserSkillRequest request) {
        User user = getUser(userId);

        Skill skill = getSkill(request.skillId());

        validateUserSkillNotExist(userId, request.skillId());

        UserSkill userSkill = UserSkill.builder()
                .user(user)
                .skill(skill)
                .build();

        return userSkillMapper.toResponse(userSkillRepository.save(userSkill));
    }

    @Transactional
    public List<UserSkillResponse> create(Long userId, CreateUserSkillsRequest request) {

        Set<Long> uniqueSkillIds = new HashSet<>(request.skillIds());

        if (uniqueSkillIds.size() != request.skillIds().size()) {
            throw new DuplicateResourceException("Duplicate skill ids are not allowed.");
        }

        User user = getUser(userId);

        List<Skill> skills = skillRepository.findByIdInAndDeletedAtIsNull(new ArrayList<>(uniqueSkillIds));

        if (skills.size() != uniqueSkillIds.size()) {
            throw new ResourceNotFoundException("Some skill ids do not exist.");
        }

        Set<Long> existingSkillIds = userSkillRepository.findSkillIdsByUserId(userId);

        List<UserSkill> userSkills = new ArrayList<>(skills.size());

        for (Skill skill : skills) {
            if (existingSkillIds.contains(skill.getId())) {
                throw new DuplicateResourceException(
                        "UserSkill already exists."
                );
            }

            UserSkill userSkill = UserSkill.builder()
                    .user(user)
                    .skill(skill)
                    .build();

            userSkills.add(userSkill);
        }

        return userSkillRepository.saveAll(userSkills)
                .stream()
                .map(userSkillMapper::toResponse)
                .toList();
    }


    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<UserSkillResponse> findByUserId(Long userId) {
        validateUserExist(userId);

        return userSkillRepository.findByUserId(userId)
                .stream()
                .map(userSkillMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserSkillResponse> findBySkillId(Long skillId) {
        validateSkillExist(skillId);

        return userSkillRepository.findBySkillId(skillId)
                .stream()
                .map(userSkillMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserSkillResponse findByUserIdAndSkillId(Long userId, Long skillId) {
        validateUserExist(userId);

        validateSkillExist(skillId);

        return userSkillMapper.toResponse(getUserSkill(userId, skillId));
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public DeleteUserSkillResponse deleteById(Long userId, Long skillId) {
        validateUserSkillExist(userId,skillId);
        UserSkill userSkill = getUserSkill(userId, skillId);
        userSkillRepository.delete(userSkill);
        return new DeleteUserSkillResponse("User skill association deleted successfully.");
    }
    // ─── Helper ────────────────────────────────────────────────

    private void validateUserSkillNotExist(Long userId, Long skillId) {
        if (userSkillRepository.existsByUserIdAndSkillId(userId, skillId))
            throw new DuplicateResourceException("UserSkill already exists.");
    }

    private void validateUserSkillExist(Long userId, Long skillId) {
        if (!userSkillRepository.existsByUserIdAndSkillId(userId, skillId))
            throw new ResourceNotFoundException("UserSkill not found.");
    }

    private UserSkill getUserSkill(Long userId, Long skillId) {
        return userSkillRepository.findByUserIdAndSkillId(userId, skillId)
                .orElseThrow(() -> new ResourceNotFoundException("User skill not found."));
    }

    private User getUser(Long userId) {
        return userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    private void validateUserExist(Long id) {
        if (!userRepository.existsByIdAndDeletedAtIsNull(id)) {
            throw new DuplicateResourceException("User not found.");
        }
    }

    private Skill getSkill(Long id) {
        return skillRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found."));
    }

    private void validateSkillExist(Long id) {
        if (!skillRepository.existsByIdAndDeletedAtIsNull(id))
            throw new DuplicateResourceException("Skill not found.");
    }
}
