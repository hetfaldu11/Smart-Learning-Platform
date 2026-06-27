package com.fm.smartlearningplatform.course.dto.enrollment.response;

import com.fm.smartlearningplatform.course.model.EnrollmentStatus;

import java.time.LocalDateTime;

public record EnrollmentResponse(

        Long id,

        Long userId,
        String userName,

        Long courseId,
        String courseTitle,
        EnrollmentStatus enrollmentStatus,
        LocalDateTime completedAt

) {
}