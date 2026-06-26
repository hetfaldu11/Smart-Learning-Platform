package com.fm.smartlearningplatform.lesson.repository;

import com.fm.smartlearningplatform.lesson.model.Lesson;
import com.fm.smartlearningplatform.lesson.model.LessonStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    // ─── Find ─────────────────────────────────────────────────

    Optional<Lesson> findByIdAndDeletedAtIsNull(
            Long lessonId
    );

    Page<Lesson> findBySectionIdAndDeletedAtIsNullOrderByPositionAsc(
            Long sectionId,
            Pageable pageable
    );

    Page<Lesson> findBySectionIdAndStatusAndDeletedAtIsNullOrderByPositionAsc(
            Long sectionId,
            LessonStatus status,
            Pageable pageable
    );

    // ─── Exists ───────────────────────────────────────────────

    boolean existsByIdAndDeletedAtIsNull(
            Long lessonId
    );

    boolean existsBySectionIdAndPositionAndDeletedAtIsNull(
            Long sectionId,
            Integer position
    );

    boolean existsBySectionIdAndPositionAndIdNotAndDeletedAtIsNull(
            Long sectionId,
            Integer position,
            Long lessonId
    );

    // ─── Count ────────────────────────────────────────────────

    long countBySectionIdAndDeletedAtIsNull(
            Long sectionId
    );

    long countBySectionIdAndStatusAndDeletedAtIsNull(
            Long sectionId,
            LessonStatus status
    );
}