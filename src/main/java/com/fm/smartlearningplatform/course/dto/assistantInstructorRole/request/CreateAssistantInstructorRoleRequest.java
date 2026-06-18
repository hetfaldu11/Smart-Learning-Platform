package com.fm.smartlearningplatform.course.dto.assistantInstructorRole.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAssistantInstructorRoleRequest(

        @NotBlank(message = "Assistant instructor role name is required.")
        @Size(
                min = 2,
                max = 100,
                message = "Assistant instructor role name must be between 2 and 100 characters."
        )
        String name

) {

    public CreateAssistantInstructorRoleRequest {

        if (name != null) {
            name = name
                    .trim()
                    .replaceAll("\\s+", " ")
                    .toLowerCase();

            if (name.isBlank()) {
                name = null;
            }
        }
    }
}