package com.fm.smartlearningplatform.dto.user.role.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateRoleRequest(

        @NotBlank(message = "Role name is required")
        String name

) {
}
