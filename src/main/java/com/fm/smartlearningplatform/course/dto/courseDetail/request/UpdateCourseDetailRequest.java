package com.fm.smartlearningplatform.course.dto.courseDetail.request;

import jakarta.validation.constraints.Size;

public record UpdateCourseDetailRequest(

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

    public UpdateCourseDetailRequest {

        if (description != null) {

            description = description
                    .trim()
                    .replaceAll("\\s+", " ");

            if (description.isBlank()) {
                description = null;
            }
        }
    }
}