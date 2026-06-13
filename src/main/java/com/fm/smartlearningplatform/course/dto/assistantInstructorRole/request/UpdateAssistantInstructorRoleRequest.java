package com.fm.smartlearningplatform.course.dto.assistantInstructorRole.request;

import jakarta.validation.constraints.Size;

public record UpdateAssistantInstructorRoleRequest(

        @Size(
                min = 2,
                max = 100,
                message = "Assistant instructor role name must be between 2 and 100 characters."
        )
        String name

) {

    public UpdateAssistantInstructorRoleRequest {

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