package com.fm.smartlearningplatform.dto.user.profession.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfessionRequest(

        @NotBlank(message = "Profession name is required")
        String name

) {
}
