package com.fm.smartlearningplatform.user.dto.interest.request;

import jakarta.validation.constraints.NotBlank;

public record CreateInterestRequest(

        @NotBlank(message = "Interest name is required")
        String name

) {
    public CreateInterestRequest {
        if (name != null) {
            name = name.trim().replaceAll("\\s+", " ").toLowerCase();
        }
    }
}
