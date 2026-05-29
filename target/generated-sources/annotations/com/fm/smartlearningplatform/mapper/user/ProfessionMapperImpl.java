package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.profession.request.CreateProfessionRequest;
import com.fm.smartlearningplatform.dto.user.profession.request.UpdateProfessionRequest;
import com.fm.smartlearningplatform.dto.user.profession.response.ProfessionResponse;
import com.fm.smartlearningplatform.model.user.Profession;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-29T14:44:43+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class ProfessionMapperImpl implements ProfessionMapper {

    @Override
    public Profession toEntity(CreateProfessionRequest request) {
        if ( request == null ) {
            return null;
        }

        Profession.ProfessionBuilder profession = Profession.builder();

        profession.name( request.getName() );

        return profession.build();
    }

    @Override
    public ProfessionResponse toResponse(Profession profession) {
        if ( profession == null ) {
            return null;
        }

        ProfessionResponse.ProfessionResponseBuilder professionResponse = ProfessionResponse.builder();

        professionResponse.id( profession.getId() );
        professionResponse.name( profession.getName() );

        return professionResponse.build();
    }

    @Override
    public void updateProfessionFromRequest(UpdateProfessionRequest request, Profession profession) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            profession.setName( request.getName() );
        }
    }
}
