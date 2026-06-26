package com.fm.smartlearningplatform.lesson.dto.lessonProgress.response;



import java.time.LocalDateTime;

public record LessonProgressResponse(

        Long id,

        Long userId,

        Long lessonId,

        String lessonTitle,

        Integer watchedSeconds,

        Integer lastPositionSeconds,

        Integer lessonDurationSeconds,

//        Double progressPercentage,

        Boolean completed,

        LocalDateTime completedAt

) {
}
