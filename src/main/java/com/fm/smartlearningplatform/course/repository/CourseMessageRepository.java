package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseMessageRepository
        extends JpaRepository<CourseMessage, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Records
     |--------------------------------------------------------------------------
     */

    Optional<CourseMessage>
    findByIdAndCourseDeletedAtIsNull(
            Long id
    );



    /*
     |--------------------------------------------------------------------------
     | Course Messages
     |--------------------------------------------------------------------------
     */

    List<CourseMessage>
    findByCourseIdAndCourseDeletedAtIsNull(
            Long courseId
    );

    Page<CourseMessage>
    findByCourseIdAndCourseDeletedAtIsNull(
            Long courseId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Message Type
     |--------------------------------------------------------------------------
     */

    List<CourseMessage>
    findByCourseMessageTypeIdAndCourseDeletedAtIsNull(
            Long messageTypeId
    );

    Page<CourseMessage>
    findByCourseMessageTypeIdAndCourseDeletedAtIsNull(
            Long messageTypeId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Specific Record
     |--------------------------------------------------------------------------
     */

    Optional<CourseMessage>
    findByCourseIdAndCourseMessageTypeIdAndCourseDeletedAtIsNull(
            Long courseId,
            Long messageTypeId
    );



    /*
     |--------------------------------------------------------------------------
     | Validation / Existence
     |--------------------------------------------------------------------------
     */

    boolean existsByIdAndCourseDeletedAtIsNull(
            Long id
    );

    boolean existsByCourseIdAndCourseMessageTypeIdAndCourseDeletedAtIsNull(
            Long courseId,
            Long messageTypeId
    );

    boolean existsByIdNotAndCourseIdAndCourseMessageTypeIdAndCourseDeletedAtIsNull(
            Long id,
            Long courseId,
            Long messageTypeId
    );



    /*
     |--------------------------------------------------------------------------
     | Search
     |--------------------------------------------------------------------------
     */

    Page<CourseMessage>
    findByCourseDeletedAtIsNullAndMessageContainingIgnoreCase(
            String message,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Sorting
     |--------------------------------------------------------------------------
     */

    List<CourseMessage>
    findByCourseIdAndCourseDeletedAtIsNullOrderByIdAsc(
            Long courseId
    );

}