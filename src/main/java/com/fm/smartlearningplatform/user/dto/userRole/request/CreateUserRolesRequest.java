package com.fm.smartlearningplatform.user.dto.userRole.request;

import java.util.List;

public record CreateUserRolesRequest(
        List<Long> roleIds
) {
}
