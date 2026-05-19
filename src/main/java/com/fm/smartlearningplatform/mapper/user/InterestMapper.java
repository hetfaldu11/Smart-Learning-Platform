package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.interest.request.CreateInterestRequest;
import com.fm.smartlearningplatform.dto.user.interest.request.UpdateInterestRequest;
import com.fm.smartlearningplatform.dto.user.interest.response.InterestResponse;
import com.fm.smartlearningplatform.model.user.Interest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InterestMapper {

    Interest toEntity(CreateInterestRequest request);

    InterestResponse toResponse(Interest interest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInterestFromRequest(UpdateInterestRequest request, @MappingTarget Interest interest);
}