package com.fm.smartlearningplatform.course.dto.courseLevel.response;

import java.time.LocalDateTime;

public record CourseLevelResponse(

        Long id,

        String name,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}