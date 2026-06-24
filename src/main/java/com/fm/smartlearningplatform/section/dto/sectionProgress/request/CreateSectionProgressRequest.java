package com.fm.smartlearningplatform.section.dto.sectionProgress.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateSectionProgressRequest(

        @NotNull(message = "user id required")
        Long userId,

        @NotNull(message = "section id required")
        Long sectionId,

        @NotNull(message = "total lesson  required")
        @Min(0)
        Integer totalLessons

) {
}