package com.fm.smartlearningplatform.security.dto;

public record AuthResponse(
        String acessToken,
        String refreshToken
) {
}
