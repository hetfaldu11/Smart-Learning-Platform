package com.fm.smartlearningplatform.course.dto.courseMessage.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCourseMessageRequest(

        @NotNull(message = "Course id is required.")
        Long courseId,

        @NotNull(message = "Course message type id is required.")
        Long courseMessageTypeId,

        @NotBlank(message = "Course message is required.")
        @Size(
                min = 3,
                max = 5000,
                message = "Course message must be between 3 and 5000 characters."
        )
        String message

) {

    public CreateCourseMessageRequest {

        if (message != null) {
            message = message
                    .trim()
                    .replaceAll("\\s+", " ");
        }
    }
}