package com.fm.smartlearningplatform.user.dto.role.request;

import jakarta.validation.constraints.NotBlank;

public record CreateRoleRequest(

        @NotBlank(message = "Role name is required")
        String name

) {
    public CreateRoleRequest {
        if (name != null) {
            name = name.trim().replaceAll("\\s+", " ").toLowerCase();
        }
    }
}
