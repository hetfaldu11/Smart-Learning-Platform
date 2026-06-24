package com.fm.smartlearningplatform.section.dto.section.response;


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
