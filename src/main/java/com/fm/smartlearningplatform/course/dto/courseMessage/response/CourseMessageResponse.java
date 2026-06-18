package com.fm.smartlearningplatform.course.dto.courseMessage.response;

public record CourseMessageResponse(

        Long id,

        Long courseId,
        String courseTitle,

        Long courseMessageTypeId,
        String courseMessageTypeName,

        String message

) {
}