package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.roleAuthority.response.RoleAuthorityResponse;
import com.fm.smartlearningplatform.user.model.RoleAuthority;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleAuthorityMapper {
    @Mapping(target = "roleId", source = "role.id")
    @Mapping(target = "authorityId", source = "authority.id")
    @Mapping(target = "authorityName", source = "authority.name")
    RoleAuthorityResponse toResponse(RoleAuthority roleAuthority);
}
