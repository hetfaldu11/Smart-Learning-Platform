package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.language.request.CreateLanguageRequest;
import com.fm.smartlearningplatform.user.dto.language.request.UpdateLanguageRequest;
import com.fm.smartlearningplatform.user.dto.language.response.LanguageResponse;
import com.fm.smartlearningplatform.user.model.Language;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-08T19:44:46+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class LanguageMapperImpl implements LanguageMapper {

    @Override
    public Language toEntity(CreateLanguageRequest request) {
        if ( request == null ) {
            return null;
        }

        Language.LanguageBuilder language = Language.builder();

        language.name( request.name() );
        language.code( request.code() );

        return language.build();
    }

    @Override
    public LanguageResponse toResponse(Language language) {
        if ( language == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String code = null;

        id = language.getId();
        name = language.getName();
        code = language.getCode();

        LanguageResponse languageResponse = new LanguageResponse( id, name, code );

        return languageResponse;
    }

    @Override
    public void updateLanguageFromRequest(UpdateLanguageRequest request, Language language) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            language.setName( request.name() );
        }
        if ( request.code() != null ) {
            language.setCode( request.code() );
        }
    }
}
