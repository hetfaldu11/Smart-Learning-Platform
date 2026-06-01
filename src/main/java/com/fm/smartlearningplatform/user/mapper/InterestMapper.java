package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.interest.request.CreateInterestRequest;
import com.fm.smartlearningplatform.user.dto.interest.request.UpdateInterestRequest;
import com.fm.smartlearningplatform.user.dto.interest.response.InterestResponse;
import com.fm.smartlearningplatform.user.model.Interest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InterestMapper {

    Interest toEntity(CreateInterestRequest request);

    InterestResponse toResponse(Interest interest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInterestFromRequest(UpdateInterestRequest request, @MappingTarget Interest interest);
}