package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.authority.request.CreateAuthorityRequest;
import com.fm.smartlearningplatform.user.dto.authority.request.UpdateAuthorityRequest;
import com.fm.smartlearningplatform.user.dto.authority.response.AuthorityResponse;
import com.fm.smartlearningplatform.user.model.Authority;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorityMapper {

    Authority toEntity(CreateAuthorityRequest request);

    AuthorityResponse toResponse(Authority authority);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAuthorityFromRequest(UpdateAuthorityRequest request, @MappingTarget Authority authority);
}