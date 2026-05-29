package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.theme.request.CreateThemeRequest;
import com.fm.smartlearningplatform.dto.user.theme.request.UpdateThemeRequest;
import com.fm.smartlearningplatform.dto.user.theme.response.ThemeResponse;
import com.fm.smartlearningplatform.model.user.Theme;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-29T14:44:43+0530",
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

        theme.name( request.getName() );

        return theme.build();
    }

    @Override
    public ThemeResponse toResponse(Theme theme) {
        if ( theme == null ) {
            return null;
        }

        ThemeResponse.ThemeResponseBuilder themeResponse = ThemeResponse.builder();

        themeResponse.id( theme.getId() );
        themeResponse.name( theme.getName() );

        return themeResponse.build();
    }

    @Override
    public void updateThemeFromRequest(UpdateThemeRequest request, Theme theme) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            theme.setName( request.getName() );
        }
    }
}
