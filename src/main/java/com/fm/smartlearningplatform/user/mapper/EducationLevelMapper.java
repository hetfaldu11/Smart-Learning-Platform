package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.educationLevel.request.CreateEducationLevelRequest;
import com.fm.smartlearningplatform.user.dto.educationLevel.request.UpdateEducationLevelRequest;
import com.fm.smartlearningplatform.user.dto.educationLevel.response.EducationLevelResponse;
import com.fm.smartlearningplatform.user.model.EducationLevel;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EducationLevelMapper {

    EducationLevel toEntity(CreateEducationLevelRequest request);

    EducationLevelResponse toResponse(EducationLevel educationLevel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEducationLevelFromRequest(UpdateEducationLevelRequest request, @MappingTarget EducationLevel educationLevel);
}