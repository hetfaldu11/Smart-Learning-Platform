package com.fm.smartlearningplatform.verification.dto.request;

public record PasswordResetRequest (

        Long userId,
        int expirySeconds,
        int resendOtpSeconds
){
}
