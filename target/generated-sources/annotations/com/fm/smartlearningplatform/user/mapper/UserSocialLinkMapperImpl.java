package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.userSocialLink.request.CreateUserSocialLinkRequest;
import com.fm.smartlearningplatform.user.dto.userSocialLink.request.PatchUserSocialLinkRequest;
import com.fm.smartlearningplatform.user.dto.userSocialLink.response.UserSocialLinkResponse;
import com.fm.smartlearningplatform.user.model.Platform;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserSocialLink;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-01T00:13:48+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class UserSocialLinkMapperImpl implements UserSocialLinkMapper {

    @Override
    public UserSocialLink toEntity(CreateUserSocialLinkRequest request, User user, Platform platform) {
        if ( request == null && user == null && platform == null ) {
            return null;
        }

        UserSocialLink.UserSocialLinkBuilder userSocialLink = UserSocialLink.builder();

        if ( request != null ) {
            userSocialLink.url( request.url() );
        }
        userSocialLink.user( user );
        userSocialLink.platform( platform );

        return userSocialLink.build();
    }

    @Override
    public UserSocialLinkResponse toResponse(UserSocialLink userSocialLink) {
        if ( userSocialLink == null ) {
            return null;
        }

        Long platformId = null;
        Long id = null;
        String url = null;

        platformId = userSocialLinkPlatformId( userSocialLink );
        id = userSocialLink.getId();
        url = userSocialLink.getUrl();

        UserSocialLinkResponse userSocialLinkResponse = new UserSocialLinkResponse( id, platformId, url );

        return userSocialLinkResponse;
    }

    @Override
    public void update(PatchUserSocialLinkRequest request, UserSocialLink userSocialLink) {
        if ( request == null ) {
            return;
        }

        if ( request.url() != null ) {
            userSocialLink.setUrl( request.url() );
        }
    }

    private Long userSocialLinkPlatformId(UserSocialLink userSocialLink) {
        Platform platform = userSocialLink.getPlatform();
        if ( platform == null ) {
            return null;
        }
        return platform.getId();
    }
}
