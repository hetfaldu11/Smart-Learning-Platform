package com.fm.smartlearningplatform.payment.dto.request;


import java.math.BigDecimal;

public record OrderPrice(

        BigDecimal subtotalAmount,

        BigDecimal discountAmount,

        BigDecimal taxableAmount,

        BigDecimal taxAmount,

        BigDecimal totalAmount

) {
}