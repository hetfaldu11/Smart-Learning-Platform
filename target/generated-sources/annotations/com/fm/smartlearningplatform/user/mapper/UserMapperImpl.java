package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.user.request.CreateUserRequest;
import com.fm.smartlearningplatform.user.dto.user.request.PatchUserRequest;
import com.fm.smartlearningplatform.user.dto.user.response.UserResponse;
import com.fm.smartlearningplatform.user.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-01T00:13:48+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(CreateUserRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( request.email() );
        user.phoneNumber( request.phoneNumber() );

        return user.build();
    }

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String email = null;
        String phoneNumber = null;
        boolean enabled = false;

        id = user.getId();
        email = user.getEmail();
        phoneNumber = user.getPhoneNumber();
        enabled = user.isEnabled();

        UserResponse userResponse = new UserResponse( id, email, phoneNumber, enabled );

        return userResponse;
    }

    @Override
    public void update(PatchUserRequest request, User user) {
        if ( request == null ) {
            return;
        }

        if ( request.email() != null ) {
            user.setEmail( request.email() );
        }
        if ( request.phoneNumber() != null ) {
            user.setPhoneNumber( request.phoneNumber() );
        }
        if ( request.enabled() != null ) {
            user.setEnabled( request.enabled() );
        }
    }
}
