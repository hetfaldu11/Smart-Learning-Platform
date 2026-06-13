package com.fm.smartlearningplatform.course.dto.currency.response;

import java.time.LocalDateTime;

public record CurrencyResponse(

        Long id,

        String name,

        String code,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}