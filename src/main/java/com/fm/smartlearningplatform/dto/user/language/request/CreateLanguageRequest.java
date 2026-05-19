package com.fm.smartlearningplatform.dto.user.language.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLanguageRequest {
    @NotBlank(message = "Name is emtpy.")
    private String name;

    @NotBlank(message = "Code is empty.")
    private String code;
}
