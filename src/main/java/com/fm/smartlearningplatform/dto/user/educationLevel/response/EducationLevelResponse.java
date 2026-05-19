package com.fm.smartlearningplatform.dto.user.educationLevel.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationLevelResponse {

    private Long id;
    private String name;
}
