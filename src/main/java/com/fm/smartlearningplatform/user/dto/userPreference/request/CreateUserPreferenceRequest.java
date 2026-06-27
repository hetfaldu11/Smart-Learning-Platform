package com.fm.smartlearningplatform.user.dto.userPreference.request;

import com.fm.smartlearningplatform.user.model.Theme;

public record CreateUserPreferenceRequest(

        Long languageId,
        Theme theme,

        Boolean notificationEnabled

) {
}