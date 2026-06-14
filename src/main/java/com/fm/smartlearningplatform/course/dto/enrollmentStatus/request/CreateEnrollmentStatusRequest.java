package com.fm.smartlearningplatform.course.dto.enrollmentStatus.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateEnrollmentStatusRequest(

        @NotBlank(message = "Enrollment status name is required.")
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

    public CreateEnrollmentStatusRequest {

        if (name != null) {
            name = name
                    .trim()
                    .replaceAll("\\s+", " ")
                    .toLowerCase();
        }

        if (description != null) {
            description = description
                    .trim()
                    .replaceAll("\\s+", " ");
        }
    }
}