package com.fm.smartlearningplatform.user.dto.language.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateLanguageRequest(

        @NotBlank(message = "Language name is required")
        String name,

        @NotBlank(message = "Language code is required")
        @Size(min = 2, max = 2, message = "Language code must be 2 length.")
        String code

) {
}