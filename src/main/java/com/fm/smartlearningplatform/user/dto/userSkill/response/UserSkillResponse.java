package com.fm.smartlearningplatform.user.dto.userSkill.response;

public record UserSkillResponse(
        Long userId,
        Long skillId,
        String skillName
) {
}
