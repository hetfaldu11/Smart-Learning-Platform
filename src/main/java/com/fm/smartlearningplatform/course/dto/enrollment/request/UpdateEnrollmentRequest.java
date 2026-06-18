package com.fm.smartlearningplatform.course.dto.enrollment.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateEnrollmentRequest(

        Long enrollmentStatusId,

        LocalDateTime completedAt

) {
}