package com.fm.smartlearningplatform.dto.user.gender.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenderResponse {
    private Long id;
    private String name;
}
