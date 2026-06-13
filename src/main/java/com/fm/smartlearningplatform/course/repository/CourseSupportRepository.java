package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseSupportRepository
        extends JpaRepository<CourseSupport, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Records
     |--------------------------------------------------------------------------
     */

    Optional<CourseSupport>
    findByIdAndCourseDeletedAtIsNull(Long courseId);

    Optional<CourseSupport>
    findByCourseIdAndCourseDeletedAtIsNull(
            Long courseId
    );



    /*
     |--------------------------------------------------------------------------
     | Support Email
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndSupportEmailIsNotNullAndCourseDeletedAtIsNull(
            Long courseId
    );



    /*
     |--------------------------------------------------------------------------
     | Support Phone
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndSupportPhoneIsNotNullAndCourseDeletedAtIsNull(
            Long courseId
    );



    /*
     |--------------------------------------------------------------------------
     | Search
     |--------------------------------------------------------------------------
     */

    Optional<CourseSupport>
    findBySupportEmailAndCourseDeletedAtIsNull(
            String supportEmail
    );

}