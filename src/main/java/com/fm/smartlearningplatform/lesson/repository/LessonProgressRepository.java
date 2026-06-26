package com.fm.smartlearningplatform.lesson.repository;

import com.fm.smartlearningplatform.lesson.model.LessonProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {

    // ─── Find ─────────────────────────────────────────────────

    Optional<LessonProgress> findById(Long lessonProgressId);

    Optional<LessonProgress> findByUserIdAndLessonId(Long userId, Long lessonId);

    Page<LessonProgress> findByUserId(Long userId, Pageable pageable);

    Page<LessonProgress> findByLessonId(Long lessonId, Pageable pageable);

    Page<LessonProgress> findByUserIdAndCompleted(Long userId, Boolean completed, Pageable pageable);

    Page<LessonProgress> findByUserIdAndCompletedFalseOrderByUpdatedAtDesc(
            Long userId,
            Pageable pageable
    );
    Page<LessonProgress>
    findByUserIdAndProgressPercentageGreaterThanEqual(
            Long userId,
            Double percentage,
            Pageable pageable
    );


    long countByUserIdAndLessonSectionIdAndCompletedTrue(
            Long userId,
            Long sectionId
    );

    // ─── Exists ───────────────────────────────────────────────

    boolean existsById(Long lessonProgressId);

    boolean existsByUserIdAndLessonId(Long userId, Long lessonId);

    // ─── Count ────────────────────────────────────────────────

    long countByUserId(Long userId);

    long countByUserIdAndCompleted(Long userId, Boolean completed);

    long countByLessonId(Long lessonId);

    long countByUserIdAndLessonSectionId(Long userId, Long sectionId);

    long countByUserIdAndLessonSectionCourseId(Long userId, Long courseId);

    long countByUserIdAndLessonSectionIdAndCompleted(Long userId, Long sectionId, Boolean completed);

    long countByUserIdAndLessonSectionCourseIdAndCompleted(Long userId, Long courseId, Boolean completed);


}

