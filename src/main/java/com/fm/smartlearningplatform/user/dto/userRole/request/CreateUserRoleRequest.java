package com.fm.smartlearningplatform.user.dto.userRole.request;

import jakarta.validation.constraints.NotNull;

public record CreateUserRoleRequest(
        @NotNull(message = "Role id is required.")
        Long roleId
) {
}