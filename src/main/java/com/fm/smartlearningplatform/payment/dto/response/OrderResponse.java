package com.fm.smartlearningplatform.payment.dto.response;

import com.fm.smartlearningplatform.payment.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(

        String orderNumber,

        Long courseId,

        BigDecimal totalAmount,

        String currency,

        OrderStatus status,

        LocalDateTime expiresAt

) {
}