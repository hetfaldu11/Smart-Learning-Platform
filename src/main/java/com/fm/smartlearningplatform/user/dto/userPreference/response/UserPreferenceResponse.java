package com.fm.smartlearningplatform.user.dto.userPreference.response;

import com.fm.smartlearningplatform.user.model.Theme;

public record UserPreferenceResponse(

        Long userId,

        Long languageId,

        Theme theme,

        boolean notificationEnabled

) {
}