package com.fm.smartlearningplatform.course.dto.courseStatus.response;

import java.time.LocalDateTime;

public record CourseStatusResponse(

        Long id,

        String name,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}