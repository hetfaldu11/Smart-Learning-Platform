package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.platform.request.CreatePlatformRequest;
import com.fm.smartlearningplatform.user.dto.platform.request.UpdatePlatformRequest;
import com.fm.smartlearningplatform.user.dto.platform.response.PlatformResponse;
import com.fm.smartlearningplatform.user.model.Platform;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-01T00:13:48+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class PlatformMapperImpl implements PlatformMapper {

    @Override
    public Platform toEntity(CreatePlatformRequest request) {
        if ( request == null ) {
            return null;
        }

        Platform.PlatformBuilder platform = Platform.builder();

        platform.name( request.name() );

        return platform.build();
    }

    @Override
    public PlatformResponse toResponse(Platform platform) {
        if ( platform == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = platform.getId();
        name = platform.getName();

        PlatformResponse platformResponse = new PlatformResponse( id, name );

        return platformResponse;
    }

    @Override
    public void updatePlatformFromRequest(UpdatePlatformRequest request, Platform platform) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            platform.setName( request.name() );
        }
    }
}
