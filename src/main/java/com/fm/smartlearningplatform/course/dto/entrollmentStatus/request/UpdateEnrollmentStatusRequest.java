package com.fm.smartlearningplatform.course.dto.enrollmentStatus.request;

import jakarta.validation.constraints.Size;

public record UpdateEnrollmentStatusRequest(

        @Size(
                min = 2,
                max = 100,
                message = "Enrollment status name must be between 2 and 100 characters."
        )
        String name,

        @Size(
                max = 5000,
                message = "Enrollment status description must not exceed 5000 characters."
        )
        String description

) {

    public UpdateEnrollmentStatusRequest {

        if (name != null) {

            name = name
                    .trim()
                    .replaceAll("\\s+", " ")
                    .toLowerCase();

            if (name.isBlank()) {
                name = null;
            }
        }

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