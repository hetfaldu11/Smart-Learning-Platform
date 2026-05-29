package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.platform.request.CreatePlatformRequest;
import com.fm.smartlearningplatform.dto.user.platform.request.UpdatePlatformRequest;
import com.fm.smartlearningplatform.dto.user.platform.response.PlatformResponse;
import com.fm.smartlearningplatform.model.user.Platform;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-29T14:44:43+0530",
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

        platform.name( request.getName() );

        return platform.build();
    }

    @Override
    public PlatformResponse toResponse(Platform platform) {
        if ( platform == null ) {
            return null;
        }

        PlatformResponse.PlatformResponseBuilder platformResponse = PlatformResponse.builder();

        platformResponse.id( platform.getId() );
        platformResponse.name( platform.getName() );

        return platformResponse.build();
    }

    @Override
    public void updatePlatformFromRequest(UpdatePlatformRequest request, Platform platform) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            platform.setName( request.getName() );
        }
    }
}
