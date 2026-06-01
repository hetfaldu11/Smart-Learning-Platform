package com.fm.smartlearningplatform.user.dto.theme.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateThemeRequest(

        @NotBlank(message = "Theme name is required")
        String name

) {
        public  UpdateThemeRequest{
                if (name != null) {
                        name = name.trim().replaceAll("\\s+", " ").toLowerCase();
                }
        }
}
