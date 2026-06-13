package com.fm.smartlearningplatform.course.dto.coursePricing.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateCoursePricingRequest(

        @NotNull(message = "Course id is required.")
        Long courseId,

        @NotNull(message = "Price is required.")
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

        @NotNull(message = "Discount price is required.")
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

        @NotNull(message = "Currency id is required.")
        Long currencyId

) {
}