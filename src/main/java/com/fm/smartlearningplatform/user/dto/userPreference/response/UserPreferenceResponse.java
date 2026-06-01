package com.fm.smartlearningplatform.user.dto.userPreference.response;

public record UserPreferenceResponse(

        Long userId,

        Long languageId,

        Long themeId,

        boolean notificationEnabled

) {
}