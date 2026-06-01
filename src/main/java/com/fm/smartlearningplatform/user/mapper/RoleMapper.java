package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.role.request.CreateRoleRequest;
import com.fm.smartlearningplatform.user.dto.role.request.UpdateRoleRequest;
import com.fm.smartlearningplatform.user.dto.role.response.RoleResponse;
import com.fm.smartlearningplatform.user.model.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    Role toEntity(CreateRoleRequest request);

    RoleResponse toResponse(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRoleFromRequest(UpdateRoleRequest request, @MappingTarget Role role);
}