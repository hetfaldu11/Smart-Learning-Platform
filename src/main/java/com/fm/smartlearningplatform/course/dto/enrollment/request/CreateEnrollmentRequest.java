package com.fm.smartlearningplatform.course.dto.enrollment.request;

import jakarta.validation.constraints.NotNull;

public record CreateEnrollmentRequest(

        @NotNull(message = "User id is required.")
        Long userId,

        @NotNull(message = "Course id is required.")
        Long courseId,

        @NotNull(message = "Enrollment status id is required.")
        Long enrollmentStatusId

) {
}