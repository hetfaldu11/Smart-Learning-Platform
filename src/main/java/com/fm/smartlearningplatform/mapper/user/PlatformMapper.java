package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.platform.request.CreatePlatformRequest;
import com.fm.smartlearningplatform.dto.user.platform.request.UpdatePlatformRequest;
import com.fm.smartlearningplatform.dto.user.platform.response.PlatformResponse;
import com.fm.smartlearningplatform.model.user.Platform;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlatformMapper {

    Platform toEntity(CreatePlatformRequest request);

    PlatformResponse toResponse(Platform platform);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePlatformFromRequest(UpdatePlatformRequest request, @MappingTarget Platform platform);
}