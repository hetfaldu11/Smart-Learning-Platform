package com.fm.smartlearningplatform.user.dto.platform.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePlatformRequest(

        @NotBlank(message = "Platform name is required")
        String name

) {
    public UpdatePlatformRequest {
        if (name != null) {
            name = name.trim().replaceAll("\\s+", " ").toLowerCase();
        }
    }
}
