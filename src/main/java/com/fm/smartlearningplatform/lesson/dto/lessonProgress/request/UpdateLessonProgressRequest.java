package com.fm.smartlearningplatform.lesson.dto.lessonProgress.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateLessonProgressRequest(

        @NotNull(message = "Watched seconds is required.")
        @PositiveOrZero(message = "Watched seconds cannot be negative.")
        Integer watchedSeconds,

        @NotNull(message = "Last position is required.")
        @PositiveOrZero(message = "Last position cannot be negative.")
        Integer lastPositionSeconds

) {
}
