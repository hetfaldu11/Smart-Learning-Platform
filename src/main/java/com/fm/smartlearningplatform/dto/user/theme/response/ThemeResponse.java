package com.fm.smartlearningplatform.dto.user.theme.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThemeResponse {

    private Long id;
    private String name;
}
