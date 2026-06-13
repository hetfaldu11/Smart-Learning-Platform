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
    findByIdAndCourseDeletedAtIsNull(Long courseId);



    /*
     |--------------------------------------------------------------------------
     | Feature Filters
     |--------------------------------------------------------------------------
     */

    Optional<CourseDetail>
    findByCourseIdAndCourseDeletedAtIsNull(
            Long courseId
    );



    /*
     |--------------------------------------------------------------------------
     | Certificate Courses
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndHasCertificateTrueAndCourseDeletedAtIsNull(
            Long courseId
    );



    /*
     |--------------------------------------------------------------------------
     | Assignment Courses
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndHasAssignmentTrueAndCourseDeletedAtIsNull(
            Long courseId
    );



    /*
     |--------------------------------------------------------------------------
     | Project Courses
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndHasProjectTrueAndCourseDeletedAtIsNull(
            Long courseId
    );



    /*
     |--------------------------------------------------------------------------
     | Quiz Courses
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndHasQuizTrueAndCourseDeletedAtIsNull(
            Long courseId
    );

}