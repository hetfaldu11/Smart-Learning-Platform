package com.fm.smartlearningplatform.dto.user.gender.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateGenderRequest(

        @NotBlank(message = "Gender name is required")
        String name

) {
}
