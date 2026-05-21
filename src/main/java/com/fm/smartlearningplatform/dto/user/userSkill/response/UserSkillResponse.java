package com.fm.smartlearningplatform.dto.user.userSkill.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillResponse {
    private Long id;
    private Long userId;
    private Long skillId;
    private String skillName;
    private LocalDateTime createdAt;
}
