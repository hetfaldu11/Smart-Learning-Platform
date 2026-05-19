package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.educationLevel.request.CreateEducationLevelRequest;
import com.fm.smartlearningplatform.dto.user.educationLevel.request.UpdateEducationLevelRequest;
import com.fm.smartlearningplatform.dto.user.educationLevel.response.EducationLevelResponse;
import com.fm.smartlearningplatform.model.user.EducationLevel;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EducationLevelMapper {

    EducationLevel toEntity(CreateEducationLevelRequest request);

    EducationLevelResponse toResponse(EducationLevel educationLevel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEducationLevelFromRequest(UpdateEducationLevelRequest request, @MappingTarget EducationLevel educationLevel);
}