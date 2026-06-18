package com.fm.smartlearningplatform.course.dto.coursePricing.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;

public record UpdateCoursePricingRequest(

        @DecimalMin(
                value = "0.0",
                inclusive = true,
                message = "Price must be greater than or equal to 0."
        )
        @Digits(
                integer = 8,
                fraction = 2,
                message = "Price format is invalid."
        )
        BigDecimal price,

        @DecimalMin(
                value = "0.0",
                inclusive = true,
                message = "Discount price must be greater than or equal to 0."
        )
        @Digits(
                integer = 8,
                fraction = 2,
                message = "Discount price format is invalid."
        )
        BigDecimal discountPrice,

        Long currencyId

) {
}