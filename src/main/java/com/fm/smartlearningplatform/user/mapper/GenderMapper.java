package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.gender.request.CreateGenderRequest;
import com.fm.smartlearningplatform.user.dto.gender.request.UpdateGenderRequest;
import com.fm.smartlearningplatform.user.dto.gender.response.GenderResponse;
import com.fm.smartlearningplatform.user.model.Gender;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenderMapper {

    Gender toEntity(CreateGenderRequest request);

    GenderResponse toResponse(Gender gender);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGenderFromRequest(UpdateGenderRequest request, @MappingTarget Gender gender);
}