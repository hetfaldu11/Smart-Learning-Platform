package com.fm.smartlearningplatform.user.dto.profession.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfessionRequest(

        @NotBlank(message = "Profession name is required")
        String name

) {
    public UpdateProfessionRequest {
        if (name != null) {
            name = name.trim().replaceAll("\\s+", " ").toLowerCase();
        }
    }
}
