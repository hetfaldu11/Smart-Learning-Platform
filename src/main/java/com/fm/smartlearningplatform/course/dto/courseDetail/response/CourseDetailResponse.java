package com.fm.smartlearningplatform.course.dto.courseDetail.response;

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