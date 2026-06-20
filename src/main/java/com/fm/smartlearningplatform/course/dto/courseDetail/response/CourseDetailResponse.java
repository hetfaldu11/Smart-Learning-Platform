package com.fm.smartlearningplatform.course.dto.courseDetail.response;

import java.time.LocalDateTime;

public record CourseDetailResponse(

        Long courseId,

        String description,

        String requirement,

        String learningOutcome,

        boolean hasCertificate,

        boolean hasAssignment,

        boolean hasProject,

        boolean hasQuiz


) {
}