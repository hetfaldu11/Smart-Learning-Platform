package com.fm.smartlearningplatform.user.dto.authority.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateAuthorityRequest(

        @NotBlank(message = "Authority name is required")
        String name

) {
    public UpdateAuthorityRequest {
        if (name != null) {
            name = name.trim().replaceAll("\\s+", " ").toLowerCase();
        }
    }
}
