package com.fm.smartlearningplatform.dto.user.skill.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSkillRequest {

    @NotBlank(message = "Name is empty.")
    private String name;
}