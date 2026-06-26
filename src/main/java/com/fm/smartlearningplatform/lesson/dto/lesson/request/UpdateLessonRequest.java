package com.fm.smartlearningplatform.lesson.dto.lesson.request;


import com.fm.smartlearningplatform.lesson.model.LessonStatus;
import com.fm.smartlearningplatform.lesson.model.LessonType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateLessonRequest(

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

        @Positive(message = "Lesson position must be greater than 0.")
        Integer position,

        @Positive(message = "Lesson duration must be greater than 0 seconds.")
        Integer durationSeconds,

        LessonType type,

        Boolean preview,

        LessonStatus status,

        LocalDateTime publishedAt,

        LocalDateTime scheduledAt

) {

    public UpdateLessonRequest {

        if (title != null) {
            title = title.trim().replaceAll("\\s+", " ");
        }

        if (description != null) {
            description = description.trim().replaceAll("\\s+", " ");
        }
    }
}
