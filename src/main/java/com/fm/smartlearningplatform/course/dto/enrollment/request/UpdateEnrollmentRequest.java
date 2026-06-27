package com.fm.smartlearningplatform.course.dto.enrollment.request;

import com.fm.smartlearningplatform.course.model.EnrollmentStatus;

import java.time.LocalDateTime;

public record UpdateEnrollmentRequest(

       EnrollmentStatus enrollmentStatus,

        LocalDateTime completedAt

) {
}