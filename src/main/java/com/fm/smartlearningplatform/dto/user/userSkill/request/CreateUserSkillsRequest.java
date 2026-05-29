package com.fm.smartlearningplatform.dto.user.userSkill.request;

import java.util.List;

public record CreateUserSkillsRequest(
        List<Long> skillIds
) {
}
