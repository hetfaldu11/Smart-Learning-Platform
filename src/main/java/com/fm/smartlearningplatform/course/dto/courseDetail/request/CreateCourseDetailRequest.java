package com.fm.smartlearningplatform.course.dto.courseDetail.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCourseDetailRequest(

        @NotNull(message = "Course id is required.")
        Long courseId,

        @NotBlank(message = "Course description is required.")
        @Size(
                min = 10,
                max = 10000,
                message = "Course description must be between 10 and 10000 characters."
        )
        String description,

        Boolean hasCertificate,

        Boolean hasAssignment,

        Boolean hasProject,

        Boolean hasQuiz

) {

    public CreateCourseDetailRequest {

        if (description != null) {
            description = description.trim()
                    .replaceAll("\\s+", " ")
                    .toLowerCase();
        }

        if (description.isBlank()) {
            description = null;
        }
    }
}