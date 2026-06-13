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
    findByIdAndCourseDeletedAtIsNull(Long courseId);

    Optional<CourseMedia>
    findByCourseIdAndCourseDeletedAtIsNull(
            Long courseId
    );



    /*
     |--------------------------------------------------------------------------
     | Thumbnail
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndThumbnailUrlIsNotNullAndCourseDeletedAtIsNull(
            Long courseId
    );



    /*
     |--------------------------------------------------------------------------
     | Promotional Video
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndPromotionalLessonUrlIsNotNullAndCourseDeletedAtIsNull(
            Long courseId
    );



    /*
     |--------------------------------------------------------------------------
     | Certificate Template
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndCertificateTemplateUrlIsNotNullAndCourseDeletedAtIsNull(
            Long courseId
    );

}