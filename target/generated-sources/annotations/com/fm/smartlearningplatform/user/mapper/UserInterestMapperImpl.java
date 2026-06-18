package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.userInterest.response.UserInterestResponse;
import com.fm.smartlearningplatform.user.model.Interest;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserInterest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-17T17:50:19+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class UserInterestMapperImpl implements UserInterestMapper {

    @Override
    public UserInterestResponse toResponse(UserInterest userInterest) {
        if ( userInterest == null ) {
            return null;
        }

        Long userId = null;
        Long interestId = null;
        String interestName = null;

        userId = userInterestUserId( userInterest );
        interestId = userInterestInterestId( userInterest );
        interestName = userInterestInterestName( userInterest );

        UserInterestResponse userInterestResponse = new UserInterestResponse( userId, interestId, interestName );

        return userInterestResponse;
    }

    private Long userInterestUserId(UserInterest userInterest) {
        User user = userInterest.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private Long userInterestInterestId(UserInterest userInterest) {
        Interest interest = userInterest.getInterest();
        if ( interest == null ) {
            return null;
        }
        return interest.getId();
    }

    private String userInterestInterestName(UserInterest userInterest) {
        Interest interest = userInterest.getInterest();
        if ( interest == null ) {
            return null;
        }
        return interest.getName();
    }
}
