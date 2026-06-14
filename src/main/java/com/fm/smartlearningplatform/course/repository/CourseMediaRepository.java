package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseMediaRepository
        extends JpaRepository<CourseMedia, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Records
     |--------------------------------------------------------------------------
     */

    Optional<CourseMedia>
    findByIdAndCourseDeletedAtIsNull(
            Long courseId
    );

    Optional<CourseMedia>
    findByCourseIdAndCourseDeletedAtIsNull(
            Long courseId
    );



    /*
     |--------------------------------------------------------------------------
     | Validation / Existence
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndCourseDeletedAtIsNull(
            Long courseId
    );

}