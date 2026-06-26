package com.fm.smartlearningplatform.lesson.dto.videoLesson.request;

import jakarta.validation.constraints.NotNull;

public record CreateVideoLessonRequest(

        @NotNull(message = "Lesson id is required.")
        Long lessonId

) {
}