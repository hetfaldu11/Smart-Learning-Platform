package com.fm.smartlearningplatform.dto.user.profession.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionResponse {

    private Long id;
    private String name;
}
