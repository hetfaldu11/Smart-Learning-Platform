package com.fm.smartlearningplatform.dto.user.authority.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateAuthorityRequest(

        @NotBlank(message = "Authority name is required")
        String name

) {
}
