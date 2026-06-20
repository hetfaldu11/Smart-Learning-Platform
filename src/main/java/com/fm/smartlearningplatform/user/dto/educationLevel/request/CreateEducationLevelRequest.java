package com.fm.smartlearningplatform.user.dto.educationLevel.request;

import jakarta.validation.constraints.NotBlank;

public record CreateEducationLevelRequest(

        @NotBlank(message = "EducationLevel name is required")
        String name

) {
    public CreateEducationLevelRequest {
        if (name != null) {
            name = name.trim().replaceAll("\\s+", " ").toLowerCase();
        }
    }
}
