package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.userPreference.request.CreateUserPreferenceRequest;
import com.fm.smartlearningplatform.user.dto.userPreference.request.PatchUserPreferenceRequest;
import com.fm.smartlearningplatform.user.dto.userPreference.response.UserPreferenceResponse;
import com.fm.smartlearningplatform.user.model.Language;
import com.fm.smartlearningplatform.user.model.Theme;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserPreference;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-01T00:13:48+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class UserPreferenceMapperImpl implements UserPreferenceMapper {

    @Override
    public UserPreference toEntity(CreateUserPreferenceRequest request, User user, Language language, Theme theme) {
        if ( request == null && user == null && language == null && theme == null ) {
            return null;
        }

        UserPreference.UserPreferenceBuilder userPreference = UserPreference.builder();

        if ( request != null ) {
            if ( request.notificationEnabled() != null ) {
                userPreference.notificationEnabled( request.notificationEnabled() );
            }
        }
        if ( user != null ) {
            userPreference.id( user.getId() );
            userPreference.user( user );
        }
        userPreference.language( language );
        userPreference.theme( theme );

        return userPreference.build();
    }

    @Override
    public UserPreferenceResponse toResponse(UserPreference preference) {
        if ( preference == null ) {
            return null;
        }

        Long userId = null;
        Long languageId = null;
        Long themeId = null;
        boolean notificationEnabled = false;

        userId = preferenceUserId( preference );
        languageId = preferenceLanguageId( preference );
        themeId = preferenceThemeId( preference );
        notificationEnabled = preference.isNotificationEnabled();

        UserPreferenceResponse userPreferenceResponse = new UserPreferenceResponse( userId, languageId, themeId, notificationEnabled );

        return userPreferenceResponse;
    }

    @Override
    public void update(PatchUserPreferenceRequest request, UserPreference preference) {
        if ( request == null ) {
            return;
        }

        if ( request.notificationEnabled() != null ) {
            preference.setNotificationEnabled( request.notificationEnabled() );
        }
    }

    private Long preferenceUserId(UserPreference userPreference) {
        User user = userPreference.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private Long preferenceLanguageId(UserPreference userPreference) {
        Language language = userPreference.getLanguage();
        if ( language == null ) {
            return null;
        }
        return language.getId();
    }

    private Long preferenceThemeId(UserPreference userPreference) {
        Theme theme = userPreference.getTheme();
        if ( theme == null ) {
            return null;
        }
        return theme.getId();
    }
}
