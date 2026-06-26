package com.fm.smartlearningplatform.lesson.dto.lesson.request;


import com.fm.smartlearningplatform.lesson.model.LessonStatus;
import com.fm.smartlearningplatform.lesson.model.LessonType;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateLessonRequest(

        @NotNull(message = "Section id is required.")
        Long sectionId,

        @NotBlank(message = "Lesson title is required.")
        @Size(
                min = 3,
                max = 200,
                message = "Lesson title must be between 3 and 200 characters."
        )
        String title,

        @Size(
                max = 5000,
                message = "Lesson description must not exceed 5000 characters."
        )
        String description,

        @NotNull(message = "Lesson position is required.")
        @Positive(message = "Lesson position must be greater than 0.")
        Integer position,

        @NotNull(message = "Lesson duration is required.")
        @Positive(message = "Lesson duration must be greater than 0 seconds.")
        Integer durationSeconds,

        @NotNull(message = "Lesson type is required.")
        LessonType type,

        @NotNull(message = "Preview flag is required.")
        Boolean preview,

        @NotNull(message = "Lesson status is required.")
        LessonStatus status,

        LocalDateTime publishedAt,

        LocalDateTime scheduledAt

) {

    public CreateLessonRequest {

        if (title != null) {
            title = title.trim().replaceAll("\\s+", " ");
        }

        if (description != null) {
            description = description.trim().replaceAll("\\s+", " ");
        }
    }
}
