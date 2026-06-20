package com.fm.smartlearningplatform.user.dto.platform.request;

import jakarta.validation.constraints.NotBlank;

public record CreatePlatformRequest(

        @NotBlank(message = "Platform name is required")
        String name

) {
    public CreatePlatformRequest {
        if (name != null) {
            name = name.trim().replaceAll("\\s+", " ").toLowerCase();
        }
    }
}
