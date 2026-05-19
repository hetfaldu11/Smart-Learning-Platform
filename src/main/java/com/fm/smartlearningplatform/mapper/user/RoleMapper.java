package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.role.request.CreateRoleRequest;
import com.fm.smartlearningplatform.dto.user.role.request.UpdateRoleRequest;
import com.fm.smartlearningplatform.dto.user.role.response.RoleResponse;
import com.fm.smartlearningplatform.model.user.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    Role toEntity(CreateRoleRequest request);

    RoleResponse toResponse(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRoleFromRequest(UpdateRoleRequest request, @MappingTarget Role role);
}