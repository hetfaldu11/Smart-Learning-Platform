package com.fm.smartlearningplatform.course.dto.enrollment.request;

import java.time.LocalDateTime;

public record UpdateEnrollmentRequest(

        Long enrollmentStatusId,

        LocalDateTime completedAt

) {
}