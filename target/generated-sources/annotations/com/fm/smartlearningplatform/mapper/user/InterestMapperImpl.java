package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.interest.request.CreateInterestRequest;
import com.fm.smartlearningplatform.dto.user.interest.request.UpdateInterestRequest;
import com.fm.smartlearningplatform.dto.user.interest.response.InterestResponse;
import com.fm.smartlearningplatform.model.user.Interest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-29T14:44:43+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class InterestMapperImpl implements InterestMapper {

    @Override
    public Interest toEntity(CreateInterestRequest request) {
        if ( request == null ) {
            return null;
        }

        Interest.InterestBuilder interest = Interest.builder();

        interest.name( request.getName() );

        return interest.build();
    }

    @Override
    public InterestResponse toResponse(Interest interest) {
        if ( interest == null ) {
            return null;
        }

        InterestResponse.InterestResponseBuilder interestResponse = InterestResponse.builder();

        interestResponse.id( interest.getId() );
        interestResponse.name( interest.getName() );

        return interestResponse.build();
    }

    @Override
    public void updateInterestFromRequest(UpdateInterestRequest request, Interest interest) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            interest.setName( request.getName() );
        }
    }
}
