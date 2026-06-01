package com.fm.smartlearningplatform.otp.dto.request;

public record CreateEmailRequest(
        int expirySeconds,
        int resendOtpSeconds
) {
}
