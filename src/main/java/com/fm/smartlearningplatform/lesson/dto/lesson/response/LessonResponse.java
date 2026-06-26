package com.fm.smartlearningplatform.lesson.dto.lesson.response;



import com.fm.smartlearningplatform.lesson.model.LessonStatus;
import com.fm.smartlearningplatform.lesson.model.LessonType;

import java.time.LocalDateTime;

public record LessonResponse(

        Long id,

        Long sectionId,

        String sectionTitle,

        String title,

        String description,

        Integer position,

        Integer durationSeconds,

        LessonType type,

        Boolean preview,

        LessonStatus status,

        LocalDateTime publishedAt,

        LocalDateTime scheduledAt,

        Long createdBy,

        Long updatedBy

) {
}
