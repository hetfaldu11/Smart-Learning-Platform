package com.fm.smartlearningplatform.user.dto.skill.request;

import jakarta.validation.constraints.NotBlank;

public record CreateSkillRequest(
        @NotBlank(message = "Skill name is required")
        String name
) {
    public CreateSkillRequest {
        if (name != null) {
            name = name.trim().replaceAll("\\s+", " ").toLowerCase();
        }
    }
}