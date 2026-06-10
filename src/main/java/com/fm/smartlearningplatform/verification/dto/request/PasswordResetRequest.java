package com.fm.smartlearningplatform.verification.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordResetRequest (

        @NotBlank
        String email

){
}
