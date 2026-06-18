package com.fm.smartlearningplatform.course.dto.courseDetail.response;

import java.time.LocalDateTime;

public record CourseDetailResponse(

        Long courseId,

        String description,

        boolean hasCertificate,

        boolean hasAssignment,

        boolean hasProject,

        boolean hasQuiz


) {
}