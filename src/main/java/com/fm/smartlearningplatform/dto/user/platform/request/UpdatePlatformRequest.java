package com.fm.smartlearningplatform.dto.user.platform.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePlatformRequest(

        @NotBlank(message = "Platform name is required")
        String name

) {
}
