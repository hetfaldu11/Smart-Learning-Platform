package com.fm.smartlearningplatform.user.dto.userSocialLink.request;

import org.hibernate.validator.constraints.URL;

public record PatchUserSocialLinkRequest(

        Long platformId,

        @URL(message = "Invalid URL format.")
        String url
) {
}
