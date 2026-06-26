package com.fm.smartlearningplatform.payment.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(

        @NotNull
        Long courseId

) {
}