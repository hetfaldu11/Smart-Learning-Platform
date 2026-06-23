package com.fm.smartlearningplatform.section.dto.response;



import java.time.LocalDateTime;

public record SectionResponse(

        Long id,

        Long courseId,

        String title,

        String description,

        Integer position,

        Integer durationSeconds,

        boolean published


) {
}
