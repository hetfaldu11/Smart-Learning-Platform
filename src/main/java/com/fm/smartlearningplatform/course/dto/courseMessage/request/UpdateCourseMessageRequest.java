package com.fm.smartlearningplatform.course.dto.courseMessage.request;

import jakarta.validation.constraints.Size;

public record UpdateCourseMessageRequest(

        Long courseMessageTypeId,

        @Size(
                min = 3,
                max = 5000,
                message = "Course message must be between 3 and 5000 characters."
        )
        String message

) {

    public UpdateCourseMessageRequest {

        if (message != null) {

            message = message
                    .trim()
                    .replaceAll("\\s+", " ");

            if (message.isBlank()) {
                message = null;
            }
        }
    }
}