package com.fm.smartlearningplatform.service.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEducationLevelRequest {

    @NotBlank(message = "Name is required.")
    private String name;
}
