package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRequirementRepository
        extends JpaRepository<CourseRequirement, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Records
     |--------------------------------------------------------------------------
     */

    Optional<CourseRequirement>
    findByIdAndCourseDeletedAtIsNull(Long id);



    /*
     |--------------------------------------------------------------------------
     | Course Requirements
     |--------------------------------------------------------------------------
     */

    List<CourseRequirement>
    findByCourseIdAndCourseDeletedAtIsNull(
            Long courseId
    );

    Page<CourseRequirement>
    findByCourseIdAndCourseDeletedAtIsNull(
            Long courseId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Search
     |--------------------------------------------------------------------------
     */

    Page<CourseRequirement>
    findByRequirementContainingIgnoreCaseAndCourseDeletedAtIsNull(
            String requirement,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Validation / Existence
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndRequirementAndCourseDeletedAtIsNull(
            Long courseId,
            String requirement
    );



    /*
     |--------------------------------------------------------------------------
     | Specific Record
     |--------------------------------------------------------------------------
     */

    Optional<CourseRequirement>
    findByCourseIdAndRequirementAndCourseDeletedAtIsNull(
            Long courseId,
            String requirement
    );

}