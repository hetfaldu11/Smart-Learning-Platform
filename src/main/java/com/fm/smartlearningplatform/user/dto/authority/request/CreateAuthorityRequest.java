package com.fm.smartlearningplatform.user.dto.authority.request;

import jakarta.validation.constraints.NotBlank;

public record CreateAuthorityRequest(

        @NotBlank(message = "Authority name is required")
        String name

) {
    public CreateAuthorityRequest {
        if (name != null) {
            name = name.trim().replaceAll("\\s+", " ").toLowerCase();
        }
    }
}
