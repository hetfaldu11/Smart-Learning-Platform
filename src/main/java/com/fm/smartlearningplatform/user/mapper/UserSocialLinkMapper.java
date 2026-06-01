package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.userSocialLink.request.CreateUserSocialLinkRequest;
import com.fm.smartlearningplatform.user.dto.userSocialLink.request.PatchUserSocialLinkRequest;
import com.fm.smartlearningplatform.user.dto.userSocialLink.response.UserSocialLinkResponse;
import com.fm.smartlearningplatform.user.model.Platform;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserSocialLink;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserSocialLinkMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "platform", source = "platform")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    UserSocialLink toEntity(
            CreateUserSocialLinkRequest request,
            User user,
            Platform platform
    );

    @Mapping(target = "platformId",
            source = "platform.id")
    UserSocialLinkResponse toResponse(
            UserSocialLink userSocialLink
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "platform", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    void update(
            PatchUserSocialLinkRequest request,
            @MappingTarget UserSocialLink userSocialLink
    );
}