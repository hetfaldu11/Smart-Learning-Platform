package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.platform.request.CreatePlatformRequest;
import com.fm.smartlearningplatform.user.dto.platform.request.UpdatePlatformRequest;
import com.fm.smartlearningplatform.user.dto.platform.response.PlatformResponse;
import com.fm.smartlearningplatform.user.model.Platform;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlatformMapper {

    Platform toEntity(CreatePlatformRequest request);

    PlatformResponse toResponse(Platform platform);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePlatformFromRequest(UpdatePlatformRequest request, @MappingTarget Platform platform);
}