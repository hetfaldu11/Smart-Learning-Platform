package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.role.request.CreateRoleRequest;
import com.fm.smartlearningplatform.user.dto.role.request.UpdateRoleRequest;
import com.fm.smartlearningplatform.user.dto.role.response.RoleResponse;
import com.fm.smartlearningplatform.user.model.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-03T19:53:27+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role toEntity(CreateRoleRequest request) {
        if ( request == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.name( request.name() );

        return role.build();
    }

    @Override
    public RoleResponse toResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = role.getId();
        name = role.getName();

        RoleResponse roleResponse = new RoleResponse( id, name );

        return roleResponse;
    }

    @Override
    public void updateRoleFromRequest(UpdateRoleRequest request, Role role) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            role.setName( request.name() );
        }
    }
}
