package com.fm.smartlearningplatform.course.dto.courseStatus.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCourseStatusRequest(

        @NotBlank(message = "Course status name is required.")
        @Size(
                min = 2,
                max = 100,
                message = "Course status name must be between 2 and 100 characters."
        )
        String name

) {

    public CreateCourseStatusRequest {

        if (name != null) {
            name = name
                    .trim()
                    .replaceAll("\\s+", " ")
                    .toLowerCase();
        }
    }
}