package com.fm.smartlearningplatform.verification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResetPasswordRequest(

        @NotBlank
        String resetToken,

        @NotBlank
        String newPassword,

        @NotNull
        Boolean logoutFromAllDevices

) {
}