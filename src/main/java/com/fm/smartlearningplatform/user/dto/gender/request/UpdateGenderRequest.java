package com.fm.smartlearningplatform.user.dto.gender.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateGenderRequest(

        @NotBlank(message = "Gender name is required")
        String name

) {
        public UpdateGenderRequest {
                if (name != null) {
                        name = name.trim().replaceAll("\\s+", " ").toLowerCase();
                }
        }
}
