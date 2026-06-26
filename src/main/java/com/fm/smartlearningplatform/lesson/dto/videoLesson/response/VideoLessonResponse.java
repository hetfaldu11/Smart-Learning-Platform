package com.fm.smartlearningplatform.lesson.dto.videoLesson.response;

public record VideoLessonResponse(

        Long id,

        Long lessonId,

        String lessonTitle,

        Long videoId,

        String videoUrl,

        Long thumbnailId,

        String thumbnailUrl

) {
}