package com.fm.smartlearningplatform.verification.dto.response;

import java.time.LocalDateTime;

public record UserVerificationResponse(

        Long id,
        Boolean emailVerified,
        Boolean phoneVerified,
        LocalDateTime emailVerifiedAt,
        LocalDateTime phoneVerifiedAt,
        Boolean twoFactorEnabled,
        LocalDateTime twoFactorEnabledAt
) {
}