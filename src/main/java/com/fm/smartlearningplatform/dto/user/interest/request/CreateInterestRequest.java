package com.fm.smartlearningplatform.dto.user.interest.request;

import jakarta.validation.constraints.NotBlank;

public record CreateInterestRequest(

        @NotBlank(message = "Interest name is required")
        String name

) {
}
