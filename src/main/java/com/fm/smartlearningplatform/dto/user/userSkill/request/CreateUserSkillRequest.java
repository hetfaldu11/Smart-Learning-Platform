package com.fm.smartlearningplatform.dto.user.userSkill.request;

import jakarta.validation.constraints.NotNull;

public record CreateUserSkillRequest(
        @NotNull(message= "Skill id is required.")
        Long skillId
) {
}