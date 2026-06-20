package com.fm.smartlearningplatform.user.dto.language.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateLanguageRequest(

        @NotBlank(message = "Language name is required")
        String name,

        @NotBlank(message = "Language code is required")
        @Size(min = 2, max = 2, message = "Language code must be 2 length.")
        String code

) {
    public CreateLanguageRequest {
        if (name != null) {
            name = name.trim().replaceAll("\\s+", " ").toLowerCase();
        }
        if (code != null) {
            code = code.trim().replaceAll("\\s+", " ").toLowerCase();
        }
    }
}
