package com.fm.smartlearningplatform.dto.user.userSkill.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserSkillRequest {
    @NotNull(message = "User id is required.")
    private Long userId;

    @NotNull(message = "Skill id is required.")
    private Long skillId;
}
