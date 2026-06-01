package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.address.AddressDto;
import com.fm.smartlearningplatform.user.dto.userProfile.request.CreateUserProfileRequest;
import com.fm.smartlearningplatform.user.dto.userProfile.request.PatchUserProfileRequest;
import com.fm.smartlearningplatform.user.dto.userProfile.response.UserProfileResponse;
import com.fm.smartlearningplatform.user.model.*;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    AddressDto toDto(Address address);

    Address toEntity(AddressDto dto);

    @Mapping(target = "id", ignore=true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "educationLevel", source = "educationLevel")
    @Mapping(target = "profession", source = "profession")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserProfile toEntity(
            CreateUserProfileRequest request,
            User user,
            EducationLevel educationLevel,
            Profession profession,
            Gender gender
    );

    @Mapping(target = "userId", source = "id")

    @Mapping(target = "educationLevelId",
            source = "educationLevel.id")

    @Mapping(target = "professionId",
            source = "profession.id")

    @Mapping(target = "genderId",
            source = "gender.id")
    UserProfileResponse toResponse(UserProfile profile);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "educationLevel", ignore = true)
    @Mapping(target = "profession", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void update(
            PatchUserProfileRequest request,
            @MappingTarget UserProfile profile
    );
}