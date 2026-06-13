package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository
        extends JpaRepository<Course, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Records
     |--------------------------------------------------------------------------
     */

    Optional<Course>
    findByIdAndDeletedAtIsNull(Long id);

    Page<Course>
    findByDeletedAtIsNull(Pageable pageable);

    Page<Course>
    findByInstructorAndDeletedAtIsNull(
            User instructor,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Deleted Records
     |--------------------------------------------------------------------------
     */

    Page<Course>
    findByDeletedAtIsNotNull(Pageable pageable);



    /*
     |--------------------------------------------------------------------------
     | Search
     |--------------------------------------------------------------------------
     */

    Page<Course>
    findByTitleContainingIgnoreCaseAndDeletedAtIsNull(
            String title,
            Pageable pageable
    );

    Page<Course>
    findBySubtitleContainingIgnoreCaseAndDeletedAtIsNull(
            String subtitle,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Course Level
     |--------------------------------------------------------------------------
     */

    Page<Course>
    findByCourseLevelIdAndDeletedAtIsNull(
            Long courseLevelId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Course Status
     |--------------------------------------------------------------------------
     */

    Page<Course>
    findByCourseStatusIdAndDeletedAtIsNull(
            Long courseStatusId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Instructor
     |--------------------------------------------------------------------------
     */

    Page<Course>
    findByInstructorIdAndDeletedAtIsNull(
            Long instructorId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Validation / Existence
     |--------------------------------------------------------------------------
     */

    boolean existsByIdAndDeletedAtIsNull(Long id);

    boolean existsByTitleAndDeletedAtIsNull(
            String title
    );

}