package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.theme.request.CreateThemeRequest;
import com.fm.smartlearningplatform.user.dto.theme.request.UpdateThemeRequest;
import com.fm.smartlearningplatform.user.dto.theme.response.ThemeResponse;
import com.fm.smartlearningplatform.user.model.Theme;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ThemeMapper {

    Theme toEntity(CreateThemeRequest request);

    ThemeResponse toResponse(Theme theme);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateThemeFromRequest(UpdateThemeRequest request, @MappingTarget Theme theme);
}