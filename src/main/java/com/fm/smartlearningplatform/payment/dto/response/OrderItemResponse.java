package com.fm.smartlearningplatform.payment.dto.response;

import java.math.BigDecimal;

public record OrderItemResponse(

        Long courseId,

        String courseTitle,

        BigDecimal originalPrice,

        BigDecimal discountAmount,

        BigDecimal finalPrice

) {
}
