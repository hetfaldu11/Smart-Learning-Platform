package com.fm.smartlearningplatform.course.dto.courseMessageType.request;

import jakarta.validation.constraints.Size;

public record UpdateCourseMessageTypeRequest(

        @Size(
                min = 2,
                max = 100,
                message = "Course message type name must be between 2 and 100 characters."
        )
        String name

) {

    public UpdateCourseMessageTypeRequest {

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