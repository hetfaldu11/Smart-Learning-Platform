package com.fm.smartlearningplatform.exceptionhandler.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ErrorResponse(

        LocalDateTime timestamp,
        int status,
        String code,
        String message,
        String path,
        Map<String, String> errors

) {
}
