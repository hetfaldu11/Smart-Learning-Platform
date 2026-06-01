package com.fm.smartlearningplatform.user.dto.roleAuthority.request;

import jakarta.validation.constraints.NotNull;

public record CreateRoleAuthorityRequest(
        @NotNull(message = "Role id is required.")
        Long roleId,
        @NotNull(message = "Authority id is required.")
        Long authorityId
) {
}