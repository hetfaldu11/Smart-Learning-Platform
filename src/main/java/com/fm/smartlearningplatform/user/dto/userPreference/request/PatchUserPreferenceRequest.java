package com.fm.smartlearningplatform.user.dto.userPreference.request;

public record PatchUserPreferenceRequest(

        Long languageId,
        Long themeId,

        Boolean notificationEnabled

) {
}