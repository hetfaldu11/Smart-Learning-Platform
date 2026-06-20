package com.fm.smartlearningplatform.user.dto.roleAuthority.request;

import jakarta.validation.constraints.NotNull;

public record DeleteRoleAuthorityRequest(
        @NotNull(message = "role id required ")
        Long roleId,

        @NotNull(message = "authority id required ")
        Long authorityId
) {
}
