package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.user.request.CreateUserRequest;
import com.fm.smartlearningplatform.user.dto.user.request.PatchUserRequest;
import com.fm.smartlearningplatform.user.dto.user.response.UserResponse;
import com.fm.smartlearningplatform.user.model.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "lastSeenAt", ignore = true)
    @Mapping(target = "passwordChangedAt", ignore = true)
    @Mapping(target = "accountLockedUntil", ignore = true)
    @Mapping(target = "failedLoginAttempt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    User toEntity(CreateUserRequest request);

    UserResponse toResponse(User user);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    void update(
            PatchUserRequest request,
            @MappingTarget User user
    );
}