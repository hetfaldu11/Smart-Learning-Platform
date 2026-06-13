package com.fm.smartlearningplatform.course.dto.courseLevel.request;

import jakarta.validation.constraints.Size;

public record UpdateCourseLevelRequest(

        @Size(
                min = 2,
                max = 100,
                message = "Course level name must be between 2 and 100 characters."
        )
        String name

) {

    public UpdateCourseLevelRequest {

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