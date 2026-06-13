package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.Enrollment;
import com.fm.smartlearningplatform.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EnrollmentRepository
        extends JpaRepository<Enrollment, Long> {

    /*
     |--------------------------------------------------------------------------
     | Find Records
     |--------------------------------------------------------------------------
     */

    Optional<Enrollment>
    findByIdAndCourseDeletedAtIsNull(Long id);

    Optional<Enrollment>
    findByUserIdAndCourseIdAndCourseDeletedAtIsNull(
            Long userId,
            Long courseId
    );



    /*
     |--------------------------------------------------------------------------
     | User Enrollments
     |--------------------------------------------------------------------------
     */

    Page<Enrollment>
    findByUserIdAndCourseDeletedAtIsNull(
            Long userId,
            Pageable pageable
    );

    Page<Enrollment>
    findByUserAndCourseDeletedAtIsNull(
            User user,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Course Enrollments
     |--------------------------------------------------------------------------
     */

    Page<Enrollment>
    findByCourseIdAndCourseDeletedAtIsNull(
            Long courseId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Enrollment Status
     |--------------------------------------------------------------------------
     */

    Page<Enrollment>
    findByEnrollmentStatusIdAndCourseDeletedAtIsNull(
            Long statusId,
            Pageable pageable
    );

    Page<Enrollment>
    findByCourseIdAndEnrollmentStatusIdAndCourseDeletedAtIsNull(
            Long courseId,
            Long statusId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Completed Enrollments
     |--------------------------------------------------------------------------
     */

    Page<Enrollment>
    findByCompletedAtIsNotNullAndCourseDeletedAtIsNull(
            Pageable pageable
    );

    Page<Enrollment>
    findByCourseIdAndCompletedAtIsNotNullAndCourseDeletedAtIsNull(
            Long courseId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Date Filters
     |--------------------------------------------------------------------------
     */

    Page<Enrollment>
    findByCreatedAtBetweenAndCourseDeletedAtIsNull(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Validation / Existence
     |--------------------------------------------------------------------------
     */

    boolean existsByUserIdAndCourseIdAndCourseDeletedAtIsNull(
            Long userId,
            Long courseId
    );

}