package com.fm.smartlearningplatform.lesson.repository;

import com.fm.smartlearningplatform.lesson.model.Lesson;
import com.fm.smartlearningplatform.lesson.model.LessonStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> { //yaa 1 min

    // =========================
    // Basic
    // =========================

    Optional<Lesson> findByIdAndDeletedAtIsNull(Long id);

    boolean existsBySectionIdAndPosition(Long sectionId, Integer position);

    // =========================
    // Section Lessons
    // =========================

    List<Lesson> findAllBySectionIdAndDeletedAtIsNullOrderByPositionAsc(Long sectionId);

    Page<Lesson> findAllBySectionIdAndDeletedAtIsNull(Long sectionId, Pageable pageable);

    // =========================
    // Published Lessons
    // =========================

    List<Lesson> findAllBySectionIdAndStatusAndDeletedAtIsNullOrderByPositionAsc(Long sectionId, LessonStatus status);

    // =========================
    // Navigation
    // =========================

    Optional<Lesson> findFirstBySectionIdAndPositionGreaterThanAndDeletedAtIsNullOrderByPositionAsc(Long sectionId, Integer position);

    Optional<Lesson> findFirstBySectionIdAndPositionLessThanAndDeletedAtIsNullOrderByPositionDesc(Long sectionId, Integer position);

    // =========================
    // Counts
    // =========================

    long countBySectionIdAndDeletedAtIsNull(Long sectionId);

    long countBySectionCourseIdAndDeletedAtIsNull(Long courseId);

    // =========================
    // Search
    // =========================

    Page<Lesson> findByTitleContainingIgnoreCaseAndDeletedAtIsNull(String keyword, Pageable pageable);

    // =========================
    // Fetch Curriculum
    // =========================

    @EntityGraph(
            attributePaths = {"section"}
    )
    List<Lesson> findAllBySectionCourseIdAndDeletedAtIsNullOrderBySectionPositionAscPositionAsc(Long courseId);

    // =========================
    // Soft Delete
    // =========================

    @Modifying
    @Query("""
            update Lesson l
            set l.deletedAt = CURRENT_TIMESTAMP
            where l.id = :lessonId
            """)
    void softDelete(@Param("lessonId") Long lessonId);
}