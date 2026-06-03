package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.profession.request.CreateProfessionRequest;
import com.fm.smartlearningplatform.user.dto.profession.request.UpdateProfessionRequest;
import com.fm.smartlearningplatform.user.dto.profession.response.ProfessionResponse;
import com.fm.smartlearningplatform.user.model.Profession;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-03T19:53:27+0530",
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

        profession.name( request.name() );

        return profession.build();
    }

    @Override
    public ProfessionResponse toResponse(Profession profession) {
        if ( profession == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = profession.getId();
        name = profession.getName();

        ProfessionResponse professionResponse = new ProfessionResponse( id, name );

        return professionResponse;
    }

    @Override
    public void updateProfessionFromRequest(UpdateProfessionRequest request, Profession profession) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            profession.setName( request.name() );
        }
    }
}
