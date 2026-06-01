package com.fm.smartlearningplatform.user.dto.interest.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateInterestRequest(

        @NotBlank(message = "Interest name is required")
        String name

) {
        public UpdateInterestRequest {
                if (name != null) {
                        name = name.trim().replaceAll("\\s+", " ").toLowerCase();
                }
        }
}
