package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.roleAuthority.response.RoleAuthorityResponse;
import com.fm.smartlearningplatform.user.model.Authority;
import com.fm.smartlearningplatform.user.model.Role;
import com.fm.smartlearningplatform.user.model.RoleAuthority;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-17T17:50:19+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class RoleAuthorityMapperImpl implements RoleAuthorityMapper {

    @Override
    public RoleAuthorityResponse toResponse(RoleAuthority roleAuthority) {
        if ( roleAuthority == null ) {
            return null;
        }

        Long roleId = null;
        Long authorityId = null;
        String authorityName = null;

        roleId = roleAuthorityRoleId( roleAuthority );
        authorityId = roleAuthorityAuthorityId( roleAuthority );
        authorityName = roleAuthorityAuthorityName( roleAuthority );

        RoleAuthorityResponse roleAuthorityResponse = new RoleAuthorityResponse( roleId, authorityId, authorityName );

        return roleAuthorityResponse;
    }

    private Long roleAuthorityRoleId(RoleAuthority roleAuthority) {
        Role role = roleAuthority.getRole();
        if ( role == null ) {
            return null;
        }
        return role.getId();
    }

    private Long roleAuthorityAuthorityId(RoleAuthority roleAuthority) {
        Authority authority = roleAuthority.getAuthority();
        if ( authority == null ) {
            return null;
        }
        return authority.getId();
    }

    private String roleAuthorityAuthorityName(RoleAuthority roleAuthority) {
        Authority authority = roleAuthority.getAuthority();
        if ( authority == null ) {
            return null;
        }
        return authority.getName();
    }
}
