package com.fm.smartlearningplatform.course.dto.courseLearningOutcome.response;

public record CourseLearningOutcomeResponse(


        Long courseId,
        String courseTitle,

        String outcome,

        Integer displayOrder

) {
}