package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.userProfile.request.CreateUserProfileRequest;
import com.fm.smartlearningplatform.user.dto.userProfile.request.PatchUserProfileRequest;
import com.fm.smartlearningplatform.user.dto.userProfile.response.UserProfileResponse;
import com.fm.smartlearningplatform.user.mapper.UserProfileMapper;
import com.fm.smartlearningplatform.user.model.*;
import com.fm.smartlearningplatform.user.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    private final EducationLevelRepository educationLevelRepository;
    private final ProfessionRepository professionRepository;
    private final GenderRepository genderRepository;

    private final UserProfileMapper userProfileMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public UserProfileResponse create(Long userId, CreateUserProfileRequest request) {

        validateProfileNotExist(userId);

        User user = getUser(userId);

        EducationLevel educationLevel = getEducationLevel(request.educationLevelId());

        Profession profession = getProfession(request.professionId());

        UserProfile profile = userProfileMapper.toEntity(request, user, educationLevel, profession);

        return userProfileMapper.toResponse(userProfileRepository.save(profile));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public UserProfileResponse findByUserId(Long userId) {
        return userProfileMapper.toResponse(getProfile(userId));
    }

    // ─── Patch ────────────────────────────────────────────────

    @Transactional
    public UserProfileResponse update(Long userId, PatchUserProfileRequest request) {

        UserProfile profile = getProfile(userId);

        if (request.educationLevelId() != null) {
            EducationLevel educationLevel = getEducationLevel(request.educationLevelId());
            profile.setEducationLevel(educationLevel);
        }

        if (request.professionId() != null) {
            Profession profession = getProfession(request.professionId());
            profile.setProfession(profession);
        }

        userProfileMapper.update(request, profile);
        return userProfileMapper.toResponse(userProfileRepository.save(profile)
        );
    }

    // ─── Helper ────────────────────────────────────────────────

    public void validateProfileNotExist(Long id) {
        if (userProfileRepository.existsById(id)) {
            throw new DuplicateResourceException("User profile already exists.");
        }
    }

    private UserProfile getProfile(Long id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found."));
    }

    private User getUser(Long id) {
        return userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    private EducationLevel getEducationLevel(Long id) {
        return educationLevelRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education level not found."));
    }

    private Profession getProfession(Long id) {
        return professionRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found."));
    }

}
