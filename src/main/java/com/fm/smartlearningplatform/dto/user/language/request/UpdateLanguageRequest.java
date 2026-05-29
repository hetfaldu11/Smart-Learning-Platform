package com.fm.smartlearningplatform.dto.user.language.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateLanguageRequest(

        @NotBlank(message = "Language name is required")
        String name

) {
}
