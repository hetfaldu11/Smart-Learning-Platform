package com.fm.smartlearningplatform.user.dto.userPreference.request;

public record CreateUserPreferenceRequest(

        Long languageId,
        Long themeId,

        Boolean notificationEnabled

) {
}