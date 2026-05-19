package com.fm.smartlearningplatform.dto.user.language.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LanguageResponse {

    private Long id;
    private String name;
    private String code;

}
