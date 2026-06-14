package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseLearningOutcome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseLearningOutcomeRepository
        extends JpaRepository<CourseLearningOutcome, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Records
     |--------------------------------------------------------------------------
     */

    Optional<CourseLearningOutcome>
    findByIdAndCourseDeletedAtIsNull(
            Long id
    );



    /*
     |--------------------------------------------------------------------------
     | Course Outcomes
     |--------------------------------------------------------------------------
     */

    List<CourseLearningOutcome>
    findByCourseIdAndCourseDeletedAtIsNullOrderByDisplayOrderAsc(
            Long courseId
    );

    Page<CourseLearningOutcome>
    findByCourseIdAndCourseDeletedAtIsNull(
            Long courseId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Search
     |--------------------------------------------------------------------------
     */

    Page<CourseLearningOutcome>
    findByCourseDeletedAtIsNullAndOutcomeContainingIgnoreCase(
            String outcome,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Validation / Existence
     |--------------------------------------------------------------------------
     */

    boolean existsByIdAndCourseDeletedAtIsNull(
            Long id
    );

    boolean existsByCourseIdAndOutcomeAndCourseDeletedAtIsNull(
            Long courseId,
            String outcome
    );

    boolean existsByIdNotAndCourseIdAndOutcomeAndCourseDeletedAtIsNull(
            Long id,
            Long courseId,
            String outcome
    );



    /*
     |--------------------------------------------------------------------------
     | Specific Record
     |--------------------------------------------------------------------------
     */

    Optional<CourseLearningOutcome>
    findByCourseIdAndOutcomeAndCourseDeletedAtIsNull(
            Long courseId,
            String outcome
    );

}