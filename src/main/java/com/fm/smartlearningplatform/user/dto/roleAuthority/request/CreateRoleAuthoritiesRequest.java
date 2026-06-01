package com.fm.smartlearningplatform.user.dto.roleAuthority.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateRoleAuthoritiesRequest(
        @NotNull(message = "Role id is required.")
        Long roleId,
        List<Long> authorityIds
) {
}
