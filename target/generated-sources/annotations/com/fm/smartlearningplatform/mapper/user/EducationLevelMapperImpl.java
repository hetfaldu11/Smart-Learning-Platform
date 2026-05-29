package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.educationLevel.request.CreateEducationLevelRequest;
import com.fm.smartlearningplatform.dto.user.educationLevel.request.UpdateEducationLevelRequest;
import com.fm.smartlearningplatform.dto.user.educationLevel.response.EducationLevelResponse;
import com.fm.smartlearningplatform.model.user.EducationLevel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-29T14:44:43+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class EducationLevelMapperImpl implements EducationLevelMapper {

    @Override
    public EducationLevel toEntity(CreateEducationLevelRequest request) {
        if ( request == null ) {
            return null;
        }

        EducationLevel.EducationLevelBuilder educationLevel = EducationLevel.builder();

        educationLevel.name( request.getName() );

        return educationLevel.build();
    }

    @Override
    public EducationLevelResponse toResponse(EducationLevel educationLevel) {
        if ( educationLevel == null ) {
            return null;
        }

        EducationLevelResponse.EducationLevelResponseBuilder educationLevelResponse = EducationLevelResponse.builder();

        educationLevelResponse.id( educationLevel.getId() );
        educationLevelResponse.name( educationLevel.getName() );

        return educationLevelResponse.build();
    }

    @Override
    public void updateEducationLevelFromRequest(UpdateEducationLevelRequest request, EducationLevel educationLevel) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            educationLevel.setName( request.getName() );
        }
    }
}
