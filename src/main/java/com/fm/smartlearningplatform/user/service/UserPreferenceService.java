package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.userPreference.request.CreateUserPreferenceRequest;
import com.fm.smartlearningplatform.user.dto.userPreference.request.PatchUserPreferenceRequest;
import com.fm.smartlearningplatform.user.dto.userPreference.response.UserPreferenceResponse;
import com.fm.smartlearningplatform.user.mapper.UserPreferenceMapper;
import com.fm.smartlearningplatform.user.model.Language;
import com.fm.smartlearningplatform.user.model.Theme;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserPreference;
import com.fm.smartlearningplatform.user.repository.LanguageRepository;
import com.fm.smartlearningplatform.user.repository.ThemeRepository;
import com.fm.smartlearningplatform.user.repository.UserPreferenceRepository;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPreferenceService {

    public final UserRepository userRepository;

    public final UserPreferenceRepository userPreferenceRepository;

    public final LanguageRepository languageRepository;

    public final ThemeRepository themeRepository;

    public final UserPreferenceMapper userPreferenceMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public UserPreferenceResponse create(Long userId, CreateUserPreferenceRequest request) {
        validatePreferenceNotExist(userId);

        User user = getUser(userId);

        Language language = getLanguage(request.languageId());

//        Theme theme = getTheme(request.themeId());

        UserPreference preference = userPreferenceMapper.toEntity(request, user, language);

        return userPreferenceMapper.toResponse(userPreferenceRepository.save(preference));
    }

    // ─── Update ─────────────────────────────────────

    @Transactional
    public UserPreferenceResponse update(Long userId, PatchUserPreferenceRequest request) {

        UserPreference preference = getPreference(userId);

        if (request.languageId() != null) {
            Language language = getLanguage(request.languageId());
            preference.setLanguage(language);
        }

        userPreferenceMapper.update(request, preference);
        return userPreferenceMapper.toResponse(userPreferenceRepository.save(preference));
    }


    // ─── Find ─────────────────────────────────────
    @Transactional(readOnly = true)
    public UserPreferenceResponse findByUserId(Long id) {
        return userPreferenceMapper.toResponse(getPreference(id));
    }

    // ─── Helper ─────────────────────────────────────

    private void validatePreferenceNotExist(Long userId) {
        if (userPreferenceRepository.existsById(userId)) {
            throw new DuplicateResourceException("User preference already exists.");
        }
    }

    private UserPreference getPreference(Long id) {
        return userPreferenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User preference not found."));
    }

    private User getUser(Long id) {
        return userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    private Language getLanguage(Long id) {
        return languageRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found."));
    }
//
//    private Theme getTheme(Long id) {
//        return themeRepository.findByIdAndDeletedAtIsNull(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Theme not found."));
//    }
}