package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.educationLevel.request.CreateEducationLevelRequest;
import com.fm.smartlearningplatform.user.dto.educationLevel.request.UpdateEducationLevelRequest;
import com.fm.smartlearningplatform.user.dto.educationLevel.response.EducationLevelResponse;
import com.fm.smartlearningplatform.user.model.EducationLevel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-19T19:53:00+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class EducationLevelMapperImpl implements EducationLevelMapper {

    @Override
    public EducationLevel toEntity(CreateEducationLevelRequest request) {
        if ( request == null ) {
            return null;
        }

        EducationLevel.EducationLevelBuilder educationLevel = EducationLevel.builder();

        educationLevel.name( request.name() );

        return educationLevel.build();
    }

    @Override
    public EducationLevelResponse toResponse(EducationLevel educationLevel) {
        if ( educationLevel == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = educationLevel.getId();
        name = educationLevel.getName();

        EducationLevelResponse educationLevelResponse = new EducationLevelResponse( id, name );

        return educationLevelResponse;
    }

    @Override
    public void updateEducationLevelFromRequest(UpdateEducationLevelRequest request, EducationLevel educationLevel) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            educationLevel.setName( request.name() );
        }
    }
}
