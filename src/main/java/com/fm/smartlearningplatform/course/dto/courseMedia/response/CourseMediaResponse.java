package com.fm.smartlearningplatform.course.dto.courseMedia.response;

import java.time.LocalDateTime;

public record CourseMediaResponse(

        Long courseId,

        String thumbnailUrl,

        String promotionalLessonUrl,

        String certificateTemplateUrl

) {
}