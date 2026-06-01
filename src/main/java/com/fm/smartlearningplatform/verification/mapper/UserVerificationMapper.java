package com.fm.smartlearningplatform.verification.mapper;

import com.fm.smartlearningplatform.verification.dto.response.UserVerificationResponse;
import com.fm.smartlearningplatform.verification.model.UserVerification;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserVerificationMapper {

    UserVerificationResponse toResponse(UserVerification userVerification);
}