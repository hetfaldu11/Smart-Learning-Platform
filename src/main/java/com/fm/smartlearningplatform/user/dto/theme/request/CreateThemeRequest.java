package com.fm.smartlearningplatform.user.dto.theme.request;

import jakarta.validation.constraints.NotBlank;

public record CreateThemeRequest(

        @NotBlank(message = "Theme name is required")
        String name

) {
        public  CreateThemeRequest{
                if (name != null) {
                        name = name.trim().replaceAll("\\s+", " ").toLowerCase();
                }
        }
}
