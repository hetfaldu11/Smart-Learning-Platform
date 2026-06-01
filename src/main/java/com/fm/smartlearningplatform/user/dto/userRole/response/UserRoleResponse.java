package com.fm.smartlearningplatform.user.dto.userRole.response;

public record UserRoleResponse(
        Long userId,
        Long roleId,
        String roleName
) {
}
