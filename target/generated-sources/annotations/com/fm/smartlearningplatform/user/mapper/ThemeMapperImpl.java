package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.theme.request.CreateThemeRequest;
import com.fm.smartlearningplatform.user.dto.theme.request.UpdateThemeRequest;
import com.fm.smartlearningplatform.user.dto.theme.response.ThemeResponse;
import com.fm.smartlearningplatform.user.model.Theme;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-01T00:13:48+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class ThemeMapperImpl implements ThemeMapper {

    @Override
    public Theme toEntity(CreateThemeRequest request) {
        if ( request == null ) {
            return null;
        }

        Theme.ThemeBuilder theme = Theme.builder();

        theme.name( request.name() );

        return theme.build();
    }

    @Override
    public ThemeResponse toResponse(Theme theme) {
        if ( theme == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = theme.getId();
        name = theme.getName();

        ThemeResponse themeResponse = new ThemeResponse( id, name );

        return themeResponse;
    }

    @Override
    public void updateThemeFromRequest(UpdateThemeRequest request, Theme theme) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            theme.setName( request.name() );
        }
    }
}
