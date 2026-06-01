package com.fm.smartlearningplatform.user.dto.profession.request;

import jakarta.validation.constraints.NotBlank;

public record CreateProfessionRequest(

        @NotBlank(message = "Profession name is required")
        String name

) {
}
