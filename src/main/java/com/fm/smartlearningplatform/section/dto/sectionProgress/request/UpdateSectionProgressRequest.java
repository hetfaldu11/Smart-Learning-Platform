package com.fm.smartlearningplatform.section.dto.sectionProgress.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateSectionProgressRequest(

        @NotNull
        @Min(0)
        Integer completedLessons
) {
}
