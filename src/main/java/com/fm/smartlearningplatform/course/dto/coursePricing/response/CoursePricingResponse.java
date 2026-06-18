package com.fm.smartlearningplatform.course.dto.coursePricing.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CoursePricingResponse(

        Long courseId,

        BigDecimal price,

        BigDecimal discountPrice,

        Long currencyId,
        String currencyName,
        String currencyCode,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}