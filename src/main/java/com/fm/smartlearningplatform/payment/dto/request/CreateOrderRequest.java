package com.fm.smartlearningplatform.payment.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(


        @NotEmpty(message = "At least one course is required.")
        List<Long> courseIds


) {
}