package com.fm.smartlearningplatform.verification.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(

        @NotBlank
        String resetToken,

        @NotBlank
        String newPassword

) {
}