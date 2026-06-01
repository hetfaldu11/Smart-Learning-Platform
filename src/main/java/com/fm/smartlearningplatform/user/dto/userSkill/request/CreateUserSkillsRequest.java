package com.fm.smartlearningplatform.user.dto.userSkill.request;

import java.util.List;

public record CreateUserSkillsRequest(
        List<Long> skillIds
) {
}
