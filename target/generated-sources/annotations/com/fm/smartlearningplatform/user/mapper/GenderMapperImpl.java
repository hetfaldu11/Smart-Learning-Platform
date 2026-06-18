package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.gender.request.CreateGenderRequest;
import com.fm.smartlearningplatform.user.dto.gender.request.UpdateGenderRequest;
import com.fm.smartlearningplatform.user.dto.gender.response.GenderResponse;
import com.fm.smartlearningplatform.user.model.Gender;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-17T17:50:19+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class GenderMapperImpl implements GenderMapper {

    @Override
    public Gender toEntity(CreateGenderRequest request) {
        if ( request == null ) {
            return null;
        }

        Gender.GenderBuilder gender = Gender.builder();

        gender.name( request.name() );

        return gender.build();
    }

    @Override
    public GenderResponse toResponse(Gender gender) {
        if ( gender == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = gender.getId();
        name = gender.getName();

        GenderResponse genderResponse = new GenderResponse( id, name );

        return genderResponse;
    }

    @Override
    public void updateGenderFromRequest(UpdateGenderRequest request, Gender gender) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            gender.setName( request.name() );
        }
    }
}
