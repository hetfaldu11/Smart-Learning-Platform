package com.fm.smartlearningplatform.user.dto.educationLevel.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateEducationLevelRequest(

        @NotBlank(message = "EducationLevel name is required")
        String name

) {
        public UpdateEducationLevelRequest {
                if (name != null) {
                        name = name.trim().replaceAll("\\s+", " ").toLowerCase();
                }
        }
}
