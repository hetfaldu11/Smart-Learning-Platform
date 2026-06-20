package com.fm.smartlearningplatform.course.dto.courseDetail.response;

public record CourseDetailResponse(

        Long courseId,

        String description,

        boolean hasCertificate,

        boolean hasAssignment,

        boolean hasProject,

        boolean hasQuiz


) {
}