package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.authority.request.CreateAuthorityRequest;
import com.fm.smartlearningplatform.user.dto.authority.request.UpdateAuthorityRequest;
import com.fm.smartlearningplatform.user.dto.authority.response.AuthorityResponse;
import com.fm.smartlearningplatform.user.model.Authority;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-01T15:04:24+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class AuthorityMapperImpl implements AuthorityMapper {

    @Override
    public Authority toEntity(CreateAuthorityRequest request) {
        if ( request == null ) {
            return null;
        }

        Authority.AuthorityBuilder authority = Authority.builder();

        authority.name( request.name() );

        return authority.build();
    }

    @Override
    public AuthorityResponse toResponse(Authority authority) {
        if ( authority == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = authority.getId();
        name = authority.getName();

        AuthorityResponse authorityResponse = new AuthorityResponse( id, name );

        return authorityResponse;
    }

    @Override
    public void updateAuthorityFromRequest(UpdateAuthorityRequest request, Authority authority) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            authority.setName( request.name() );
        }
    }
}
