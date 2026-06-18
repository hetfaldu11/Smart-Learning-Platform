package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.interest.request.CreateInterestRequest;
import com.fm.smartlearningplatform.user.dto.interest.request.UpdateInterestRequest;
import com.fm.smartlearningplatform.user.dto.interest.response.InterestResponse;
import com.fm.smartlearningplatform.user.model.Interest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-17T17:50:19+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class InterestMapperImpl implements InterestMapper {

    @Override
    public Interest toEntity(CreateInterestRequest request) {
        if ( request == null ) {
            return null;
        }

        Interest.InterestBuilder interest = Interest.builder();

        interest.name( request.name() );

        return interest.build();
    }

    @Override
    public InterestResponse toResponse(Interest interest) {
        if ( interest == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = interest.getId();
        name = interest.getName();

        InterestResponse interestResponse = new InterestResponse( id, name );

        return interestResponse;
    }

    @Override
    public void updateInterestFromRequest(UpdateInterestRequest request, Interest interest) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            interest.setName( request.name() );
        }
    }
}
