package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.userInterest.response.UserInterestResponse;
import com.fm.smartlearningplatform.user.model.UserInterest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserInterestMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "interestId", source = "interest.id")
    @Mapping(target = "interestName", source = "interest.name")
    UserInterestResponse toResponse(UserInterest userInterest);
}
