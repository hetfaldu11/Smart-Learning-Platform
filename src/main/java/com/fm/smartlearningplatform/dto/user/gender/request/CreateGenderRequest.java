package com.fm.smartlearningplatform.dto.user.gender.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGenderRequest {
    @NotBlank(message = "Name is empty.")
    private String name;
}
