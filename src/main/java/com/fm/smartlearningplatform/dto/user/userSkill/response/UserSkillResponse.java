package com.fm.smartlearningplatform.dto.user.userSkill.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillResponse {
    private Long userId;
    private Long skillId;
    private String skillName;
}
