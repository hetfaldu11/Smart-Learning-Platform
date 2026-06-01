package com.fm.smartlearningplatform.user.dto.userSocialLink.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record CreateUserSocialLinkRequest(

        @NotNull(message = "Platform id is required.")
        Long platformId,

        @NotBlank(message = "Url is required.")
        @URL(message = "Invalid URL format.")
        String url

) {
}