package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.dto.user.userSkill.request.CreateUserSkillRequest;
import com.fm.smartlearningplatform.dto.user.userSkill.request.CreateUserSkillsRequest;
import com.fm.smartlearningplatform.dto.user.userSkill.response.DeleteUserSkillResponse;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final UserSkillMapper userSkillMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public UserSkillResponse create(Long userId,CreateUserSkillRequest request) {


        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Skill skill = skillRepository.findByIdAndDeletedAtIsNull(request.skillId())
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found."));

        if (userSkillRepository.existsByUserIdAndSkillId(userId, request.skillId()))
            throw new DuplicateResourceException("UserSkill already exists.");

        UserSkill userSkill = user.addSkill(skill);
        userRepository.save(user);

        return userSkillMapper.toResponse(userSkill);
    }

    @Transactional
    public List<UserSkillResponse> create(Long userId, CreateUserSkillsRequest request) {

        Set<Long> uniqueSkillIds = new HashSet<>(request.skillIds());

        if (uniqueSkillIds.size() != request.skillIds().size()) {
            throw new DuplicateResourceException(
                    "Duplicate skill ids are not allowed."
            );
        }

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        List<Skill> skills = skillRepository
                .findByIdInAndDeletedAtIsNull(
                        new ArrayList<>(uniqueSkillIds)
                );

        if (skills.size() != uniqueSkillIds.size()) {
            throw new ResourceNotFoundException(
                    "Some skill ids do not exist."
            );
        }

        Set<Long> existingSkillIds =
                userSkillRepository.findSkillIdsByUserId(userId);

        List<UserSkillResponse> responses =
                new ArrayList<>(skills.size());

        for (Skill skill : skills) {

            if (existingSkillIds.contains(skill.getId())) {
                throw new DuplicateResourceException(
                        "UserSkill already exists."
                );
            }

            UserSkill userSkill = user.addSkill(skill);

            responses.add(
                    userSkillMapper.toResponse(userSkill)
            );
        }

        userRepository.save(user);

        return responses;
    }


    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<UserSkillResponse> findByUserId(Long userId){
        if(!userRepository.existsByIdAndDeletedAtIsNull(userId))
            throw new ResourceNotFoundException("User not found.");

        return userSkillRepository.findByUserId(userId)
                .stream()
                .map(userSkillMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserSkillResponse> findBySkillId(Long skillId){
        if(!skillRepository.existsByIdAndDeletedAtIsNull(skillId))
            throw new ResourceNotFoundException("Skill not found.");

        return userSkillRepository.findBySkillId(skillId)
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
    public DeleteUserSkillResponse deleteById(Long id){
        UserSkill userSkill = userSkillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserSkill not found."));
        userSkillRepository.delete(userSkill);

        return new DeleteUserSkillResponse("User skill association deleted successfully.");
    }
}