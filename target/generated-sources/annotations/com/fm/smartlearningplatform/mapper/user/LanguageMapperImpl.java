package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.language.request.CreateLanguageRequest;
import com.fm.smartlearningplatform.dto.user.language.request.UpdateLanguageRequest;
import com.fm.smartlearningplatform.dto.user.language.response.LanguageResponse;
import com.fm.smartlearningplatform.model.user.Language;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-29T14:44:43+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class LanguageMapperImpl implements LanguageMapper {

    @Override
    public Language toEntity(CreateLanguageRequest request) {
        if ( request == null ) {
            return null;
        }

        Language.LanguageBuilder language = Language.builder();

        language.name( request.getName() );
        language.code( request.getCode() );

        return language.build();
    }

    @Override
    public LanguageResponse toResponse(Language language) {
        if ( language == null ) {
            return null;
        }

        LanguageResponse.LanguageResponseBuilder languageResponse = LanguageResponse.builder();

        languageResponse.id( language.getId() );
        languageResponse.name( language.getName() );
        languageResponse.code( language.getCode() );

        return languageResponse.build();
    }

    @Override
    public void updateLanguageFromRequest(UpdateLanguageRequest request, Language language) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            language.setName( request.getName() );
        }
        if ( request.getCode() != null ) {
            language.setCode( request.getCode() );
        }
    }
}
