package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CoursePricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoursePricingRepository
        extends JpaRepository<CoursePricing, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Records
     |--------------------------------------------------------------------------
     */

    Optional<CoursePricing>
    findByIdAndCourseDeletedAtIsNull(
            Long courseId
    );

    Optional<CoursePricing>
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