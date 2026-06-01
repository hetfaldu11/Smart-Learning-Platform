package com.fm.smartlearningplatform.user.dto.role.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateRoleRequest(

        @NotBlank(message = "Role name is required")
        String name

) {
}
