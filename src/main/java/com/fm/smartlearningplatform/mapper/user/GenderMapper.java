package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.gender.request.CreateGenderRequest;
import com.fm.smartlearningplatform.dto.user.gender.request.UpdateGenderRequest;
import com.fm.smartlearningplatform.dto.user.gender.response.GenderResponse;
import com.fm.smartlearningplatform.model.user.Gender;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenderMapper {

    Gender toEntity(CreateGenderRequest request);

    GenderResponse toResponse(Gender gender);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGenderFromRequest(UpdateGenderRequest request, @MappingTarget Gender gender);
}