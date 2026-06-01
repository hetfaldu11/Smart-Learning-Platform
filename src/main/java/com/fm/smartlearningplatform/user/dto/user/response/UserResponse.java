package com.fm.smartlearningplatform.user.dto.user.response;


import java.time.LocalDateTime;

public record UserResponse(

        Long id,

        String email,

        String phoneNumber,

        boolean enabled
) {
}
