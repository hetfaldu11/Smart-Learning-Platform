package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseDetailRepository
        extends JpaRepository<CourseDetail, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Record
     |--------------------------------------------------------------------------
     */

    Optional<CourseDetail>
    findByIdAndCourseDeletedAtIsNull(
            Long courseId
    );

    Optional<CourseDetail>
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