package com.fm.smartlearningplatform.user.dto.user.response;


public record UserResponse(

        Long id,

        String email,

        String phoneNumber,

        boolean enabled
) {
}
