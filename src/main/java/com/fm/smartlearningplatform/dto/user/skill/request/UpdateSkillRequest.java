package com.fm.smartlearningplatform.dto.user.skill.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSkillRequest {
    @NotBlank(message = "Name is empty.")
    private String name;
}
