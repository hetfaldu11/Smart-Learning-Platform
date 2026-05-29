package com.fm.smartlearningplatform.dto.user.educationLevel.request;

import jakarta.validation.constraints.NotBlank;

public record CreateEducationLevelRequest(

        @NotBlank(message = "EducationLevel name is required")
        String name

) {
}
