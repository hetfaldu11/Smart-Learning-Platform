package com.fm.smartlearningplatform.dto.user.authority.request;

import jakarta.validation.constraints.NotBlank;

public record CreateAuthorityRequest(

        @NotBlank(message = "Authority name is required")
        String name

) {
}
