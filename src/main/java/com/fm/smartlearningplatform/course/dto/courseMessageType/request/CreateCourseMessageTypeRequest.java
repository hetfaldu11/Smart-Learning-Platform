package com.fm.smartlearningplatform.course.dto.courseMessageType.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCourseMessageTypeRequest(

        @NotBlank(message = "Course message type name is required.")
        @Size(
                min = 2,
                max = 100,
                message = "Course message type name must be between 2 and 100 characters."
        )
        String name

) {

    public CreateCourseMessageTypeRequest {

        if (name != null) {
            name = name
                    .trim()
                    .replaceAll("\\s+", " ")
                    .toLowerCase();
        }
    }
}