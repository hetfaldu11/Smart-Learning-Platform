package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.gender.request.CreateGenderRequest;
import com.fm.smartlearningplatform.dto.user.gender.request.UpdateGenderRequest;
import com.fm.smartlearningplatform.dto.user.gender.response.GenderResponse;
import com.fm.smartlearningplatform.model.user.Gender;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-29T14:44:43+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class GenderMapperImpl implements GenderMapper {

    @Override
    public Gender toEntity(CreateGenderRequest request) {
        if ( request == null ) {
            return null;
        }

        Gender.GenderBuilder gender = Gender.builder();

        gender.name( request.getName() );

        return gender.build();
    }

    @Override
    public GenderResponse toResponse(Gender gender) {
        if ( gender == null ) {
            return null;
        }

        GenderResponse.GenderResponseBuilder genderResponse = GenderResponse.builder();

        genderResponse.id( gender.getId() );
        genderResponse.name( gender.getName() );

        return genderResponse.build();
    }

    @Override
    public void updateGenderFromRequest(UpdateGenderRequest request, Gender gender) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            gender.setName( request.getName() );
        }
    }
}
