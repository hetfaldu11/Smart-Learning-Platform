package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.userRole.response.UserRoleResponse;
import com.fm.smartlearningplatform.user.model.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRoleMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "roleId", source = "role.id")
    @Mapping(target = "roleName", source = "role.name")
    UserRoleResponse toResponse(UserRole userRole);
}
