package com.fm.smartlearningplatform.verification.dto.request;

import jakarta.validation.constraints.NotBlank;

public record VerifyResetOtpRequest(

        @NotBlank
        String email,

        @NotBlank
        String otp

) {
}