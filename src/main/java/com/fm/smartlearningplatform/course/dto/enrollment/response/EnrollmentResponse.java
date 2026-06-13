package com.fm.smartlearningplatform.course.dto.enrollment.response;

import java.time.LocalDateTime;

public record EnrollmentResponse(

        Long id,

        Long userId,
        String userName,

        Long courseId,
        String courseTitle,

        Long enrollmentStatusId,
        String enrollmentStatusName,

        LocalDateTime completedAt,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}