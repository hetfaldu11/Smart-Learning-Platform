package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.userPreference.request.CreateUserPreferenceRequest;
import com.fm.smartlearningplatform.user.dto.userPreference.request.PatchUserPreferenceRequest;
import com.fm.smartlearningplatform.user.dto.userPreference.response.UserPreferenceResponse;
import com.fm.smartlearningplatform.user.model.Language;
import com.fm.smartlearningplatform.user.model.Theme;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserPreference;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserPreferenceMapper {

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "theme", source = "theme")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserPreference toEntity(
            CreateUserPreferenceRequest request,
            User user,
            Language language,
            Theme theme
    );

    @Mapping(target = "userId", source = "user.id")

    @Mapping(target = "languageId",
            source = "language.id")

    @Mapping(target = "themeId",
            source = "theme.id")
    UserPreferenceResponse toResponse(
            UserPreference preference
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "theme", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void update(
            PatchUserPreferenceRequest request,
            @MappingTarget UserPreference preference
    );
}