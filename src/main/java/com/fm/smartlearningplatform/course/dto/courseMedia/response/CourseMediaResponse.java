package com.fm.smartlearningplatform.course.dto.courseMedia.response;

public record CourseMediaResponse(

        Long courseId,

        String thumbnailUrl,

        String promotionalLessonUrl,

        String certificateTemplateUrl

) {
}