package com.fm.smartlearningplatform.verification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VerifyResetOtpRequest(

        @NotNull
        Long userId,

        @NotBlank
        String otp

) {
}