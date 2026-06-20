package com.fm.smartlearningplatform.verification.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank(message = "old password required:")
        String oldPassword,

        @NotBlank(message = "new password requird: ")
        String newPassword
) {
}
