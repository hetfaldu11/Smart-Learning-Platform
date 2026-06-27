package com.fm.smartlearningplatform.payment.dto.response;


import com.fm.smartlearningplatform.payment.model.enums.CurrencyCode;
import com.fm.smartlearningplatform.payment.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(

        Long id,

        String orderNumber,

        OrderStatus status,

        CurrencyCode currency,

        BigDecimal subtotalAmount,

        BigDecimal discountAmount,

        BigDecimal taxAmount,

        BigDecimal totalAmount,

        LocalDateTime expiresAt,

        List<OrderItemResponse> items

) {
}