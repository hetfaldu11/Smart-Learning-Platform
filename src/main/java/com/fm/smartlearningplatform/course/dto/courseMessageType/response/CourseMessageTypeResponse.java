package com.fm.smartlearningplatform.course.dto.courseMessageType.response;

import java.time.LocalDateTime;

public record CourseMessageTypeResponse(

        Long id,

        String name,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}