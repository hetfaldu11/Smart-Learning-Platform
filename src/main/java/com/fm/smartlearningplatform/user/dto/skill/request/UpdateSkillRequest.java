package com.fm.smartlearningplatform.user.dto.skill.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateSkillRequest(

        @NotBlank(message = "Skill name is required")
        String name

) {
    public  UpdateSkillRequest{
        if (name != null) {
            name = name.trim().replaceAll("\\s+", " ").toLowerCase();
        }
    }
}
