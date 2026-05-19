package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.theme.request.CreateThemeRequest;
import com.fm.smartlearningplatform.dto.user.theme.request.UpdateThemeRequest;
import com.fm.smartlearningplatform.dto.user.theme.response.ThemeResponse;
import com.fm.smartlearningplatform.model.user.Theme;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ThemeMapper {

    Theme toEntity(CreateThemeRequest request);

    ThemeResponse toResponse(Theme theme);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateThemeFromRequest(UpdateThemeRequest request, @MappingTarget Theme theme);
}