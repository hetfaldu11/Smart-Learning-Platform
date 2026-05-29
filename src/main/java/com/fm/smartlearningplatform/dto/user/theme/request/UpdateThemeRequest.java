package com.fm.smartlearningplatform.dto.user.theme.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateThemeRequest(

        @NotBlank(message = "Theme name is required")
        String name

) {
}
