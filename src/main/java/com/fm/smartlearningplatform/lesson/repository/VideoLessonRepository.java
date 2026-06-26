package com.fm.smartlearningplatform.lesson.repository;

import com.fm.smartlearningplatform.lesson.model.VideoLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoLessonRepository
        extends JpaRepository<VideoLesson, Long> {

    // ─── Find ─────────────────────────────────────────────────

    Optional<VideoLesson> findByLessonId(
            Long lessonId
    );

    // ─── Exists ───────────────────────────────────────────────

    boolean existsByLessonId(
            Long lessonId
    );

    boolean existsByVideoId(
            String videoId
    );

    boolean existsByThumbnailId(
            String thumbnailId
    );
}