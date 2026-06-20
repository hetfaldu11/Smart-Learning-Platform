package com.fm.smartlearningplatform.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email(message = "Email is not valid.")
        @NotBlank(message = "Email is required.")
        String email,

        @NotBlank(message = "Password is required.")
        String password,

        Boolean trusted
) {

    public LoginRequest {
        if (email != null) {
            email = email.trim().replaceAll("\\s+", " ").toLowerCase();
        }
        if (password != null) {
            password = password.trim();
        }
    }
}
