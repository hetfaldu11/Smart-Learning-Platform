package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Language;
import com.fm.smartlearningplatform.model.user.Theme;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserPreference;
import com.fm.smartlearningplatform.repository.user.LanguageRepository;
import com.fm.smartlearningplatform.repository.user.ThemeRepository;
import com.fm.smartlearningplatform.repository.user.UserPreferenceRepository;
import com.fm.smartlearningplatform.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPreferenceService {

    UserRepository userRepository;

    UserPreferenceRepository userPreferenceRepository;

    LanguageRepository languageRepository;

    ThemeRepository themeRepository;

    @Autowired
    public UserPreferenceService(UserRepository userRepository,
                                 UserPreferenceRepository userPreferenceRepository,
                                 LanguageRepository languageRepository,
                                 ThemeRepository themeRepository) {

        this.userRepository = userRepository;
        this.userPreferenceRepository = userPreferenceRepository;
        this.languageRepository = languageRepository;
        this.themeRepository = themeRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public User addUserPreference(User user, UserPreference userPreference){

        if(userRepository.existsByIdAndDeletedAtIsNull(user.getId()))
            throw new RuntimeException("User not found.");

        if(userPreferenceRepository.existsById(user.getId())){
            throw new RuntimeException("User preference already exists.");
        }

        if(userPreference.getLanguage() != null && languageRepository.existsById(userPreference.getLanguage().getId()) == false){
            throw new RuntimeException("Language not found.");
        }

        if(userPreference.getTheme() != null && themeRepository.existsById(userPreference.getTheme().getId()) == false){
            throw new RuntimeException("Theme not found.");
        }

        user.setUserPreference(userPreference);

        return userRepository.save(user);
    }

    @Transactional
    public User addUserPreference(Long userId, UserPreference userPreference){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if(userPreferenceRepository.existsById(userId)){
            throw new RuntimeException("User preference already exists.");
        }

        if(userPreference.getLanguage() != null && languageRepository.existsById(userPreference.getLanguage().getId()) == false){
            throw new RuntimeException("Language not found.");
        }

        if(userPreference.getTheme() != null && themeRepository.existsById(userPreference.getTheme().getId()) == false){
            throw new RuntimeException("Theme not found.");
        }

        user.setUserPreference(userPreference);

        return userRepository.save(user);
    }

    // ─── Update ─────────────────────────────────────

    @Transactional
    public User updateLanguage(Long userId, Language newLanguage){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        UserPreference userPreference = user.getUserPreference();

        if(userPreference == null){
            throw new RuntimeException("User preference not found.");
        }

        Language language = languageRepository.findById(newLanguage.getId())
                .orElseThrow(() -> new RuntimeException("Language not found."));

        userPreference.setLanguage(language);

        return userRepository.save(user);
    }


    @Transactional
    public User updateTheme(Long userId, Theme newTheme){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        UserPreference userPreference = user.getUserPreference();

        if(userPreference == null){
            throw new RuntimeException("User preference not found.");
        }

        Theme theme = themeRepository.findById(newTheme.getId())
                .orElseThrow(() -> new RuntimeException("Theme not found."));

        userPreference.setTheme(theme);

        return userRepository.save(user);
    }


    @Transactional
    public User updateNotificationEnabled(Long userId, boolean notificationEnabled){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        UserPreference userPreference = user.getUserPreference();

        if(userPreference == null){
            throw new RuntimeException("User preference not found.");
        }

        userPreference.setNotificationEnabled(notificationEnabled);

        return userRepository.save(user);
    }
}