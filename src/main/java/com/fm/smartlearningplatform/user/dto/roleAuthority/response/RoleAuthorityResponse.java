package com.fm.smartlearningplatform.user.dto.roleAuthority.response;

public record RoleAuthorityResponse(
        Long roleId,
        Long authorityId,
        String authorityName
) {
}
