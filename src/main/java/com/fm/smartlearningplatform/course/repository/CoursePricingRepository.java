package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CoursePricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
    findByIdAndCourseDeletedAtIsNull(Long courseId);

    Optional<CoursePricing>
    findByCourseIdAndCourseDeletedAtIsNull(
            Long courseId
    );



    /*
     |--------------------------------------------------------------------------
     | Currency
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndCurrencyIdAndCourseDeletedAtIsNull(
            Long courseId,
            Long currencyId
    );



    /*
     |--------------------------------------------------------------------------
     | Discount Validation
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndDiscountPriceGreaterThanAndCourseDeletedAtIsNull(
            Long courseId,
            BigDecimal discountPrice
    );



    /*
     |--------------------------------------------------------------------------
     | Price Filters
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndPriceGreaterThanAndCourseDeletedAtIsNull(
            Long courseId,
            BigDecimal price
    );

}